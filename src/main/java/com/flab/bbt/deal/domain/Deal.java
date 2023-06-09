package com.flab.bbt.deal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.bbt.exception.CustomException;
import com.flab.bbt.exception.ErrorCode;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Deal {

    private Long id;
    private Long productId;
    private int groupSize; // PriceTable 스냅샷. 목표인원
    private int discountPrice; // PriceTable 스냅샷. 할인가
    @Setter
    private DealStatus status;
    private Long priceTableId;

    @Setter
    private int participantCount;
    private boolean isPrivate;
    private LocalDateTime expiredAt; // 마감되는 일시
    private LocalDateTime closedAt; // 성사된 일시
    private int version;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonProperty("isPrivate")
    public boolean getIsPrivate() {
        return this.isPrivate;
    }

    public DealStatus getStatus() {
        if (LocalDateTime.now().isAfter(this.getExpiredAt())) {
            return DealStatus.EXPIRED;
        }
        return status;
    }

    public void incrementParticipantCount(int count) {
        int updatedCount = this.getParticipantCount() + count;
        if (updatedCount > this.getGroupSize()) {
            throw new CustomException(ErrorCode.DEAL_GROUP_SIZE_EXCEEDED);
        } else if (updatedCount < this.getGroupSize()) {
            this.setParticipantCount(updatedCount);
            this.setStatus(DealStatus.IN_PROGRESS);
        } else {
            this.setParticipantCount(updatedCount);
            this.setStatus(DealStatus.COMPLETED);
        }
    }

    public boolean isJoinable(int count) {
        return (this.getGroupSize() > this.getParticipantCount() + count);
    }

}
