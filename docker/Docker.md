# Docker

## Docker安装

### CentOS

#### 卸载旧版本

```sh
 $ sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-selinux \
                  docker-engine-selinux \
                  docker-engine
```

#### 使用 yum安装

```sh
$ sudo yum install -y yum-utils
```

#### 添加 yum 软件源

```sh
$ sudo yum-config-manager \
    --add-repo \
    https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

$ sudo sed -i 's/download.docker.com/mirrors.aliyun.com\/docker-ce/g' /etc/yum.repos.d/docker-ce.repo

# 官方源
# $ sudo yum-config-manager \
#     --add-repo \
#     https://download.docker.com/linux/centos/docker-ce.repo
```

#### CentOS8 额外设置

由于 CentOS8 防火墙使用了 `nftables`，但 Docker 尚未支持 `nftables`， 我们可以使用如下设置使用 `iptables`：

更改 `/etc/firewalld/firewalld.conf`

```sh
# FirewallBackend=nftables
FirewallBackend=iptables
```

```sh
$ firewall-cmd --permanent --zone=trusted --add-interface=docker0

$ firewall-cmd --reload
```

## Build Ship and Run(搭建，发送，运行)

- **Build（构建镜像）** ： 镜像就像是集装箱包括文件以及运行环境等等资源。
- **Ship（运输镜像）** ：主机和仓库间运输，这里的仓库就像是超级码头一样。
- **Run （运行镜像）** ：运行的镜像就是一个容器，容器就是运行程序的地方。

> eg：
>
> 1. 想建一个房子，搬石头、砍木头、画图纸，盖好了，住了一段时间，想要搬到别的地方去，按照以往的方法只能再次重复前面的步骤
> 2. 跑来一个巫婆，使用模仿将盖好的房子复制一份，做成“镜像”，放在背包里
> 3. 到了另一块空地，只需要利用“镜像”，复制一套房子就可以拎包入住

## Build once，Run anywhere

> 放在背包里的“镜像”，就是**Docker镜像**，背包就是**Docker仓库**。在空地用魔法建好的房子，就是**Docker容器**。

## Docker 基本概念

- **镜像（Image）：特殊的文件系统**

  Docker镜像除了提供容器运行时所需要的程序、库、资源、配置等文件外，还包含了一些为运行时准备的配置参数（匿名卷、环境变量、用户等）。镜像不包含任何动态数据，其内容在构建之后也不会被改变。

> Union FS技术 -> 分层存储架构。镜像实际是由多层文件系统联合组成的。

- **容器（Container）：镜像运行时的实体**

  镜像（Image）和容器（Container）的关系，就像是面向对象程序设计中的 类 和 实例 一样，镜像是静态的定义，**容器是镜像运行时的实体。容器可以被创建、启动、停止、删除、暂停等** 。

  **容器的实质是进程，但与直接在宿主执行的进程不同，容器进程运行于属于自己的独立的 命名空间。前面讲过镜像使用的是分层存储，容器也是如此。**

  **容器存储层的生存周期和容器一样，容器消亡时，容器存储层也随之消亡。因此，任何保存于容器存储层的信息都会随容器删除而丢失。**

- **仓库（Repository）：集中存放镜像文件的地方**

  镜像构建完成后，可以很容易的在当前宿主上运行，但是， **如果需要在其它服务器上使用这个镜像，我们就需要一个集中的存储、分发镜像的服务，Docker Registry 就是这样的服务。**

## Docker 常见命令

1. 基本命令

```sh
docker version 		# 查看docker版本
docker images 		# 查看已经下载的镜像
docker container ls # 查看所有容器
docker ps 			# 查看正在运行的容器
docker image prune 	# 清理临时的，没有被使用的镜像文件 -a, --all: 删除所有没有用的镜像，而不仅仅是临时文件；
```

2. 拉取镜像

```sh
docker search mysql 	# 查看mysql相关镜像
docker pull mysql:5.7 	# 拉去mysql镜像
docker image ls 		# 查看所有已经下载的镜像
```

3. 删除镜像

比如我们要删除我们下载的 mysql 镜像。

通过 `docker rmi [image]` （等价于`docker image rm [image]`）删除镜像之前首先要确保这个镜像没有被容器引用（可以通过标签名称或者镜像 ID删除）。通过我们前面讲的` docker ps`命令即可查看。

```sh
➜  ~ docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                               NAMES
c4cd691d9f80        mysql:5.7           "docker-entrypoint.s…"   7 weeks ago         Up 12 days          0.0.0.0:3306->3306/tcp, 33060/tcp   mysql
```

可以看到 mysql 正在被 id 为 c4cd691d9f80 的容器引用，我们需要首先通过 `docker stop c4cd691d9f80` 或者 `docker stop mysql`暂停这个容器。

然后查看 mysql 镜像的 id

```sh
➜  ~ docker images
REPOSITORY              TAG                 IMAGE ID            CREATED             SIZE
mysql                   5.7                 f6509bac4980        3 months ago        373MB
```

通过 IMAGE ID 或者 REPOSITORY 名字即可删除

```sh
docker rmi f6509bac4980 #  或者 docker rmi mysql 
```