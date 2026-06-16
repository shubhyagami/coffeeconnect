package com.coffeeconnect.service;

import com.coffeeconnect.dto.MessageDto;
import com.coffeeconnect.entity.Connection;
import com.coffeeconnect.entity.User;
import com.coffeeconnect.repository.ConnectionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConnectionService {

    private final ConnectionRepository connectionRepository;

    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    public List<User> getConnectedUsers(User user) {
        return connectionRepository.findConnectedUsers(user);
    }

    public Page<Connection> getConnectionsPaginated(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("connectedAt").descending());
        return connectionRepository.findConnectionsForUser(user, pageable);
    }

    public long getConnectionCount(User user) {
        return connectionRepository.countConnectionsForUser(user);
    }

    public boolean areConnected(User user1, User user2) {
        return connectionRepository.existsByUserOneAndUserTwo(user1, user2) ||
               connectionRepository.existsByUserTwoAndUserOne(user1, user2);
    }

    @Transactional
    public void removeConnection(Long connectionId, User user) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found"));
        if (!connection.getUserOne().getId().equals(user.getId()) &&
            !connection.getUserTwo().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized");
        }
        connectionRepository.delete(connection);
    }
}
