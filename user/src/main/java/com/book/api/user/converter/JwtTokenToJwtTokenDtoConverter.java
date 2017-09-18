package com.book.api.user.converter;

import com.book.api.user.model.JwtToken;
import com.book.api.user.model.JwtTokenDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenToJwtTokenDtoConverter implements Converter<JwtToken, JwtTokenDto> {

    @Override
    public JwtTokenDto convert(JwtToken jwtToken) {
        JwtTokenDto dto = new JwtTokenDto();
        dto.setTokenPayload(jwtToken.getTokenPayload());
        dto.setExpiryDate(jwtToken.getExpiryDate().toString());
        return dto;
    }
}
