package com.website.demo.API.registration;


import com.cloudinary.utils.ObjectUtils;
import com.website.demo.cloudinary.CloudinaryConfig;
import com.website.demo.API.address.AddressService;
import com.website.demo.API.registration.email.EmailSender;
import com.website.demo.API.registration.token.ConfirmationToken;
import com.website.demo.API.registration.token.ConfirmationTokenService;
import com.website.demo.API.user.AppUser;
import com.website.demo.API.user.AppUserRepository;
import com.website.demo.API.user.AppUserService;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Service
@Data
public class RegistrationService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final AddressService addressService;
    private final EmailSender emailSender;
    private final CloudinaryConfig cloudinaryConfig;
    @Value("${client.url}")
    private String CLIENT_URL;


    public LocalDate birthdayToLocalDate(String birthdateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(birthdateString, formatter);
    }


    @Transactional
    public void register(AppUser appUser, MultipartFile image) {
        String email = appUser.getEmail();
        appUser.setAddress(addressService.getAddressAndSaveIfNotExists(appUser.getAddress()));
        // resending email confirmation message for existing user
        if (appUserRepository.findByEmail(email).isPresent()) {
            appUser = appUserRepository.findByEmail(appUser.getEmail()).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
            if (!appUser.isEnabled()) {
                resendEmail(appUser.getEmail(), appUser.getFirstName());
            }
        }
        System.out.println(appUser);
        String token = appUserService.signUp(appUser);
        emailSender.send(appUser.getEmail(), buildEmail(appUser.getFirstName(), CLIENT_URL + "/confirm/" + token));

        if (image != null) {
            try {
                Map uploadResult = cloudinaryConfig.getCloudinary().uploader().upload(image.getBytes(), ObjectUtils.asMap(
                        "upload_preset", "doctors"
                ));
                String publicId = uploadResult.get("public_id").toString();
                appUserService.setImg(publicId, appUser.getEmail());
            } catch (Exception e) {
                throw new RuntimeException("Image upload failed");
            }
//            // full path to project directory
//            String projectDirectory = new File("").getAbsolutePath();
//            // directory where we want our files to be stored
//            String fileDirectory = projectDirectory + "/src/main/images/doctors/";
//            String originalFilename = image.getOriginalFilename();
//            String filePath = fileDirectory + originalFilename;
//            File dest = new File(filePath);
//            try {
//                image.transferTo(dest);
//            } catch (Exception e) {
//                throw new RuntimeException("Failed to transfer file to destinated directory.");
//            }
//            appUserService.setImageName(originalFilename, appUser.getEmail());
        }
        //  return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("E-mail already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }
        confirmationTokenService.confirmToken(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        confirmationTokenService.removeToken(confirmationToken);
        return "confirmed";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    public String resendEmail(String email, String firstName) {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        emailSender.send(email, buildEmail(firstName, CLIENT_URL + "/confirm/" + token));
        return token;
    }
}
