[![Java CI with Gradle](https://github.com/1petr0v/ua-qr-payment-java-lib/actions/workflows/gradle.yml/badge.svg)](https://github.com/1petr0v/ua-qr-payment-java-lib/actions/workflows/gradle.yml) 
[![Coverage](https://raw.githubusercontent.com/1petr0v/ua-qr-payment-java-lib/badges/jacoco.svg)](https://github.com/1petr0v/ua-qr-payment-java-lib/actions/workflows/code-coverage.yaml)
# Генератор та парсер українськіх платіжних QR-кодів / Ukrainian Payment QR-codes generator and parser
QR Code Payment Lib

Згідно з постанови "Про затвердження Правил формування та використання QR-коду для здійснення кредитових переказів" https://bank.gov.ua/ua/legislation/Resolution_28052020_68

In case you see in tests question mark symbols ('?') instead of cyrillic characters on Windows or Mac:

1. Make sure that data you're providing is in UTF-8 format. This may include file encoding as well.
2. Tell you test engine the following parameters `-Dconsole.encoding=UTF-8 -Dfile.encoding=UTF-8`. For example, if you run tests in intellij, go to 'Help' -> 'Edit Custom VM Options'.
