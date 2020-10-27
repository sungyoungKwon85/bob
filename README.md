# 밥한끼


## 명령어 정리
```
// run docker containers for like mongo 
$ docker-compose up -d

// mongo 
$ docker exec -it [cid] /bin/bash
> mongo -u bob -p
> show dbs
> use test
> show collections:
> db.restaurant.count()
> db.restaurant.find({"type": "01"}).limit(1)
 
```