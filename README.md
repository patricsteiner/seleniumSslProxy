# Selenium SSL Proxy
Selenium proxy with functionality to add certificate-based client authentication for end-to-end tests.

This is how it works:

- intercept Selenium HTTP requests with BrowserMob
- setup an `SSLContext` given a certificate (.pfx file) and password 
- use [okhttp](https://github.com/square/okhttp) to forward the request to the target URL
- convert the okhttp `Response` to a netty `FullHttpResponse` so it can be handled by Selenium

## Dependencies
- selenium: 3.11.0
- browsermob: 2.1.5
- okhttp and okhttp-tls: 3.14.1
