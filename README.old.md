# webnikki Starting Guide

## Setup for localhost

It requires following software:

* Play Framework (version 2.1.0 or above)
* PostgreSQL (version 9.1.8 or above)

### Modify /etc/hosts

Add following lines to /etc/hosts

    127.0.0.1	localhost.com
    127.0.0.1	foo.localhost.com
    127.0.0.1	bar.localhost.com

*foo.localhost.com* and *bar.localhost.com* allow you to create user "foo" and "bar".  If you want to make other users, please add lines for the users.

### Create Database for webnikki

    $ createdb -O YOURUSERNAME -U YOURUSERNAME -E utf8 webnikki

### Deploy and Run

    $ git clone git@github.com:mahata/webnikki.git
    $ cd webnikki
    $ play "run -Dconfig.file=conf/dev.conf 9005"

Now, you can access to the service via http://localhost.com:9005


