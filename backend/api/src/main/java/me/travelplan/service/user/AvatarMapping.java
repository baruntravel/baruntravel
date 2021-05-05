package me.travelplan.service.user;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface AvatarMapping {
}
