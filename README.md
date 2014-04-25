# webnikki Starting Guide

## What's webnikki

nikki (日記) is a word which means diary. webnikki is a service to publish your nikki on web :)

## Requirements

* Vagrant 1.4.3 (or above)
* Ansible 1.5.4 (or above)
* Play Framework 2.1.5

## How to setup

```
(local) git clone https://github.com/mahata/webnikki.git
(local) cd webnikki
(local) vagrant up
(local) vagrant provision
(local) vagrant ssh
(vm) cd ~/src
(vm) play "run -Dconfig.file=conf/dev.conf"
```

You'll be access to http://localhost:9000/ now!

## How to hack

You can change Scala code under `PATH/TO/webnikki/src` directly since it's NFS mounted.

### How to hack with IntelliJ

Provided you have Play Framework 2.1.5 in your `$PATH` and yoru IntelliJ has Scala plugin, run following commands:

```
(local) cd /PATH/TO/webnikki/src
(local) play
(play) idea with-sources=yes
```

Once it's done, do followings:

* Open IntelliJ
* Open Project -> /PATH/TO/webnikki/src
* Happy Hacking!
