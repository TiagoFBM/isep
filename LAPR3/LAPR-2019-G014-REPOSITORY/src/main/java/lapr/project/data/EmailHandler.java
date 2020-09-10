package lapr.project.data;

import lapr.project.model.User;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;


    public class EmailHandler {
        /**
         * path for the email properties.
         */
        private final String path;
        /**
         * Subject of the email.
         */
        private String subject;
        /**
         * Text of the email.
         */
        private String text;

        /**
         * Builder method with a default path to the credentials
         */
        public EmailHandler() {
            path = "target/classes/application.properties";
        }

        /**
         * Builder method with a custom path defined by the user
         *
         * @param path Path to the file with the credentials
         */
        public EmailHandler(String path) {
            this.path = path;
        }

        /**
         * Method that sends a email to the emailAddress received, with the content received
         *
         * @return True if operation works with success, false otherwise
         */
        public boolean sendEmail(Map<User, StringBuilder> mapInvoiveUsers) {
            for (User user : mapInvoiveUsers.keySet()){
                if (user.getEmail() == null) {
                    return false;
                }

                if (!validateEmail(user.getEmail())) {
                    return false;
                }
                Properties properties = new Properties();
                try (FileInputStream file = new FileInputStream(path)) {
                    properties.load(file);
                } catch (IOException e) {
                    return false;
                }

                if (properties.getProperty("email.companyEmail", null) == null || properties.getProperty("email.password", null) == null
                        || properties.getProperty("email.server", null) == null || properties.getProperty("email.port", null) == null) {
                    return false;
                }

                if (mapInvoiveUsers.keySet()==null){
                    return false;
                }
                for (User u : mapInvoiveUsers.keySet()){
                    setUpEmail(mapInvoiveUsers.get(u));
                    String companyEmail = properties.getProperty("email.companyEmail", null);
                    String password = properties.getProperty("email.password", null);
                    String server = properties.getProperty("email.server", null);
                    String port = properties.getProperty("email.port", null);
                    sendEmail(companyEmail, user.getEmail(), server, port, password);
                }
            }
            return true;
        }

        /**
         * Given the necessary data, it sends and email.
         *
         * @param companyEmail Email of the company.
         * @param emailAddress Email of the receiver.
         * @param server       Server of the email.
         * @param port         Port of the server.
         * @param password     Password of the companies' email.
         * @return true if the email was sent, false if not.
         */
        private boolean sendEmail(String companyEmail, String emailAddress, String server, String port, String password) {

            Email email = EmailBuilder.startingBlank()
                    .from("No-Reply", companyEmail)
                    .to(emailAddress, emailAddress)
                    .withSubject(subject)
                    .withPlainText(text)
                    .buildEmail();
            try {
                Mailer m = MailerBuilder
                        .withSMTPServer(server, Integer.valueOf(port), companyEmail, password).withTransportStrategy(TransportStrategy.SMTP_TLS)
                        .buildMailer();
                if (m.validate(email)) {
                    m.sendMail(email);
                    return true;
                }
                return false;
            } catch (Exception e) {
                    return false;
            }
        }

        /**
         * Validates if the email syntax is valid.
         *
         * @param email
         * @return true if it is, false if not.
         */
        private static boolean validateEmail(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

            Pattern pat = Pattern.compile(emailRegex);
            return pat.matcher(email).matches();
        }

        /**
         * Sets up the email subject and text.
         */
        private void setUpEmail(StringBuilder stringBuilder) {

            String date = "";
            LocalDateTime time = LocalDateTime.now();
            date = date.concat(time.getDayOfMonth() + "/" + time.getMonthValue() + "/" + time.getYear()
                        + " " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());

            this.subject = "Invoices of the Month ";
            this.subject = subject.concat(date);

            this.text =stringBuilder.toString();
        }

    }
