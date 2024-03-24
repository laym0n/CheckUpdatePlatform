package com.victor.kochnev.core.service.pluginusage;

import com.victor.kochnev.core.converter.DomainPluginUsageMapper;
import com.victor.kochnev.core.dto.domain.entity.PluginUsageDto;
import com.victor.kochnev.core.dto.request.CreatePluginUsageRequestDto;
import com.victor.kochnev.core.exception.PluginUsageNotPermittedException;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.entity.PluginUsage;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.enums.PluginStatus;
import com.victor.kochnev.domain.value.object.DistributionMethod;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PluginUsageServiceImpl implements PluginUsageService {
    private final DomainPluginUsageMapper pluginUsageMapper;
    private final PluginUsageRepository pluginUsageRepository;
    private final PluginRepository pluginRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public PluginUsageDto create(CreatePluginUsageRequestDto requestDto, UUID userId) {
        PluginUsage pluginUsage = pluginUsageMapper.mapToDomain(requestDto);

        Plugin plugin = pluginRepository.getById(requestDto.getPluginId());
        pluginUsage.setPlugin(plugin);

        User user = userRepository.getById(userId);
        pluginUsage.setUser(user);

        if (plugin.getStatus() == PluginStatus.CREATED) {
            throw new PluginUsageNotPermittedException(plugin.getId());
        }

        boolean distributionMethodExists = plugin.getDescription().getDistributionMethods().stream()
                .anyMatch(method -> method.equals(requestDto.getDistributionMethod()));
        if (!distributionMethodExists) {
            throw new ResourceNotFoundException(DistributionMethod.class.getName(), requestDto.getDistributionMethod().toString(), "all");
        }

        pluginUsage = pluginUsageRepository.create(pluginUsage);
        return pluginUsageMapper.mapToDto(pluginUsage);
    }
}
