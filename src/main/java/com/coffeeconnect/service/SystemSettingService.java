package com.coffeeconnect.service;

import com.coffeeconnect.entity.SystemSetting;
import com.coffeeconnect.repository.SystemSettingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemSettingService {

    private final SystemSettingRepository systemSettingRepository;
    private final Map<String, String> cache = new HashMap<>();

    public SystemSettingService(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
        loadCache();
    }

    private void loadCache() {
        cache.clear();
        systemSettingRepository.findAll().forEach(s -> cache.put(s.getSettingKey(), s.getSettingValue()));
    }

    public String getSetting(String key, String defaultValue) {
        return cache.getOrDefault(key, defaultValue);
    }

    public boolean isEnabled(String key) {
        return "true".equalsIgnoreCase(getSetting(key, "true"));
    }

    public List<SystemSetting> getAllSettings() {
        return systemSettingRepository.findAll();
    }

    @Transactional
    public SystemSetting updateSetting(String key, String value) {
        SystemSetting setting = systemSettingRepository.findBySettingKey(key)
                .orElse(SystemSetting.builder().settingKey(key).build());
        setting.setSettingValue(value);
        SystemSetting saved = systemSettingRepository.save(setting);
        cache.put(key, value);
        return saved;
    }

    public boolean isRegistrationEnabled() {
        return isEnabled("registration.enabled");
    }

    public boolean isVerificationRequired() {
        return isEnabled("verification.required");
    }

    public boolean isMessagingEnabled() {
        return isEnabled("messaging.enabled");
    }

    public boolean areCoffeeRequestsEnabled() {
        return isEnabled("coffee_requests.enabled");
    }

    public boolean isMaintenanceMode() {
        return "true".equalsIgnoreCase(getSetting("maintenance.mode", "false"));
    }
}
