package org.example.map;

import org.mapstruct.ReportingPolicy;

/**
 *
 * @author donh
 */
@org.mapstruct.MapperConfig(unmappedTargetPolicy = ReportingPolicy.WARN)
public interface MapperConfig {
}
