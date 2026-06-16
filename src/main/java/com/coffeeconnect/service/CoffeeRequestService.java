package com.coffeeconnect.service;

import com.coffeeconnect.dto.CoffeeRequestDto;
import com.coffeeconnect.entity.CoffeeRequest;
import com.coffeeconnect.entity.Connection;
import com.coffeeconnect.entity.Notification;
import com.coffeeconnect.entity.User;
import com.coffeeconnect.enums.NotificationType;
import com.coffeeconnect.enums.RequestStatus;
import com.coffeeconnect.repository.CoffeeRequestRepository;
import com.coffeeconnect.repository.ConnectionRepository;
import com.coffeeconnect.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeRequestService {

    private final CoffeeRequestRepository coffeeRequestRepository;
    private final ConnectionRepository connectionRepository;
    private final NotificationRepository notificationRepository;

    public CoffeeRequestService(CoffeeRequestRepository coffeeRequestRepository,
                                ConnectionRepository connectionRepository,
                                NotificationRepository notificationRepository) {
        this.coffeeRequestRepository = coffeeRequestRepository;
        this.connectionRepository = connectionRepository;
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public CoffeeRequest sendRequest(User sender, User receiver, String message) {
        if (sender.getId().equals(receiver.getId())) {
            throw new RuntimeException("Cannot send request to yourself");
        }
        if (!receiver.isActive()) {
            throw new RuntimeException("Cannot send request to an inactive user");
        }
        if (coffeeRequestRepository.existsBySenderAndReceiverAndStatus(sender, receiver, RequestStatus.PENDING)) {
            throw new RuntimeException("A pending request already exists");
        }
        CoffeeRequest request = CoffeeRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .message(message)
                .status(RequestStatus.PENDING)
                .build();
        CoffeeRequest saved = coffeeRequestRepository.save(request);
        Notification notification = Notification.builder()
                .user(receiver)
                .type(NotificationType.REQUEST_RECEIVED)
                .title("New Coffee Request")
                .message(sender.getFullName() + " wants to grab coffee with you!")
                .targetUrl("/coffee-requests")
                .build();
        notificationRepository.save(notification);
        return saved;
    }

    @Transactional
    public CoffeeRequest acceptRequest(Long requestId, User receiver) {
        CoffeeRequest request = coffeeRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        if (!request.getReceiver().getId().equals(receiver.getId())) {
            throw new RuntimeException("Not authorized");
        }
        request.setStatus(RequestStatus.ACCEPTED);
        CoffeeRequest saved = coffeeRequestRepository.save(request);
        Connection connection = Connection.builder()
                .userOne(request.getSender())
                .userTwo(request.getReceiver())
                .build();
        connectionRepository.save(connection);
        Notification notification = Notification.builder()
                .user(request.getSender())
                .type(NotificationType.REQUEST_ACCEPTED)
                .title("Coffee Request Accepted")
                .message(receiver.getFullName() + " accepted your coffee request!")
                .targetUrl("/connections")
                .build();
        notificationRepository.save(notification);
        return saved;
    }

    @Transactional
    public CoffeeRequest declineRequest(Long requestId, User receiver) {
        CoffeeRequest request = coffeeRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        if (!request.getReceiver().getId().equals(receiver.getId())) {
            throw new RuntimeException("Not authorized");
        }
        request.setStatus(RequestStatus.DECLINED);
        return coffeeRequestRepository.save(request);
    }

    @Transactional
    public CoffeeRequest cancelRequest(Long requestId, User sender) {
        CoffeeRequest request = coffeeRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        if (!request.getSender().getId().equals(sender.getId())) {
            throw new RuntimeException("Not authorized");
        }
        request.setStatus(RequestStatus.CANCELLED);
        return coffeeRequestRepository.save(request);
    }

    public List<CoffeeRequestDto> getSentRequests(User sender) {
        return coffeeRequestRepository.findBySender(sender).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<CoffeeRequestDto> getReceivedRequests(User receiver) {
        return coffeeRequestRepository.findByReceiver(receiver).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Page<CoffeeRequestDto> getSentRequestsPaginated(User sender, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return coffeeRequestRepository.findBySender(sender, pageable).map(this::toDto);
    }

    public Page<CoffeeRequestDto> getReceivedRequestsPaginated(User receiver, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return coffeeRequestRepository.findByReceiver(receiver, pageable).map(this::toDto);
    }

    private CoffeeRequestDto toDto(CoffeeRequest r) {
        CoffeeRequestDto dto = new CoffeeRequestDto();
        dto.setId(r.getId());
        dto.setSenderId(r.getSender().getId());
        dto.setSenderName(r.getSender().getFullName());
        dto.setSenderEmail(r.getSender().getEmail());
        dto.setSenderProfilePic(r.getSender().getProfilePictureUrl());
        dto.setSenderDepartment(r.getSender().getDepartment());
        dto.setSenderDesignation(r.getSender().getDesignation());
        dto.setReceiverId(r.getReceiver().getId());
        dto.setReceiverName(r.getReceiver().getFullName());
        dto.setReceiverEmail(r.getReceiver().getEmail());
        dto.setReceiverProfilePic(r.getReceiver().getProfilePictureUrl());
        dto.setReceiverDepartment(r.getReceiver().getDepartment());
        dto.setReceiverDesignation(r.getReceiver().getDesignation());
        dto.setMessage(r.getMessage());
        dto.setStatus(r.getStatus().name());
        dto.setCreatedAt(r.getCreatedAt() != null ? r.getCreatedAt().toString() : "");
        return dto;
    }
}
