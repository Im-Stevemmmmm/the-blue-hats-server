package com.thebluehats.server.core.modules.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Qualifier()
@Retention(RetentionPolicy.RUNTIME)
public @interface ServerApi {
}