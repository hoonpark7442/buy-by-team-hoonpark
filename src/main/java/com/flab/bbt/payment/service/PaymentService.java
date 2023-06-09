package com.flab.bbt.payment.service;

import com.flab.bbt.exception.CustomException;
import com.flab.bbt.exception.ErrorCode;
import com.flab.bbt.payment.domain.Payment;
import com.flab.bbt.payment.domain.PaymentStatus;
import com.flab.bbt.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment createPayment(Payment payment) {
        // validation for payment --> 로직 추가 예정?

        // create payment
        paymentRepository.save(payment);
        return payment;
    }

    public Payment completePayment(Long paymentId) {

        Payment payment = findPaymentById(paymentId);
        payment.updateStatus(PaymentStatus.SUCCESS);

        // [TODO] Payment의 status 필드가 현재 PaymentStatus 타입이 아닌 int여서
        // 첫번째 파라미터에 payment.getStatus() 적용 불가 -> PaymentStatus 타입으로 변경 필요
        int result = paymentRepository.updatePaymentStatusById(PaymentStatus.SUCCESS,
            payment.getId());

        // [TODO] result가 0일시 처리

        return payment;
    }

    public Payment findPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
            .orElseThrow(() -> new CustomException(ErrorCode.PAYMENT_NOT_FOUND));
    }


}
