package io.github.jtsato.bookstore.entrypoint.rest.common;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/user")
@Controller
public class UserInfoController {

    private static final String HTML_LINE_BREAK = "<br/>";

    @RequestMapping("/oauthinfo")
    @ResponseBody
    public String oauthUserInfo(@RegisteredOAuth2AuthorizedClient final OAuth2AuthorizedClient authorizedClient,
                                @AuthenticationPrincipal final OAuth2User oauth2User) {
        return "User Name: " + oauth2User.getName()
               + HTML_LINE_BREAK
               + "User Authorities: "
               + oauth2User.getAuthorities()
               + HTML_LINE_BREAK
               + "Client Name: "
               + authorizedClient.getClientRegistration().getClientName()
               + HTML_LINE_BREAK
               + this.prettyPrintAttributes(oauth2User.getAttributes());
    }

    private String prettyPrintAttributes(final Map<String, Object> attributes) {
        final StringBuilder userAttributes = new StringBuilder("User Attributes: <br/><div style='padding-left:20px'>");
        for (final Entry<String, Object> entrySet : attributes.entrySet()) {
            userAttributes.append("<div>" + entrySet.getKey() + ":&nbsp" + entrySet.getValue().toString() + "</div>");
        }
        return userAttributes + "</div>";
    }
}
