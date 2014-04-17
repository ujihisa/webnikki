# -*- mode: ruby -*-
# vi: set ft=ruby :
#
Vagrant.configure("2") do |config|
  config.vm.box = "precise64"
  config.vm.box_url = "http://files.vagrantup.com/precise64.box"
  config.vm.hostname = "webnikki"
  config.ssh.username = "vagrant"
  config.vm.network :private_network, ip: "192.168.37.24"
  config.vm.synced_folder "playframework", "/home/vagrant/playframework", :nfs => true

  config.vm.network :forwarded_port, guest: 9005, host: 9005

  config.vm.provider :virtualbox do |vb|
    vb.customize ["modifyvm", :id, "--memory", 2048]
    vb.customize ["modifyvm", :id, "--natdnshostresolver1", "on"]
  end

  config.vm.provision :ansible do |ansible|
    ansible.playbook = "main.yml"
    ansible.inventory_path = "inventories/vagrant"
  end
end
