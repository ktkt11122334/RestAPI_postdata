# RestAPI Test Development
 
This project is a test delevopment of RestAPI.  
Basically, I implemented the contents written in the reference book, [Technology to support WEB(WEBを支える技術)](https://www.amazon.co.jp/Web%E3%82%92%E6%94%AF%E3%81%88%E3%82%8B%E6%8A%80%E8%A1%93-HTTP%E3%80%81URI%E3%80%81HTML%E3%80%81%E3%81%9D%E3%81%97%E3%81%A6REST-WEB-PRESS-plus/dp/4774142042/ref=sr_1_1_sspa?__mk_ja_JP=%E3%82%AB%E3%82%BF%E3%82%AB%E3%83%8A&dchild=1&keywords=Web%E3%82%92%E6%94%AF%E3%81%88%E3%82%8B%E6%8A%80%E8%A1%93&qid=1601822055&sr=8-1-spons&psc=1&spLa=ZW5jcnlwdGVkUXVhbGlmaWVyPUEzMFNERlBMRjYwOUY5JmVuY3J5cHRlZElkPUEwNzczMjcyMUlLWVdSMzRaUFRGNyZlbmNyeXB0ZWRBZElkPUExVzhUTFY2Uk9XVDA2JndpZGdldE5hbWU9c3BfYXRmJmFjdGlvbj1jbGlja1JlZGlyZWN0JmRvTm90TG9nQ2xpY2s9dHJ1ZQ==)
　  
　  
This project is started up with Spring Boot on Docker container.  
You can create the Docker Containers by the inscruction shown beleow and  
confirm Response Data from command(curl).  
　  
　  

## Create Docker Container

　
* Start to create docker container.


```sh

docker-compose up -d

```

　
If you have a timeout error of pulling CentOS,
please retry.
　

```sh

ERROR: Service 'centos' failed to build: error pulling image configuration: Get https://registry-1.docker.io/v2/library/centos/blobs/sha256:7e6257c9f8d8d4cdff5e155f196d67150b871bbe8c02761026f803a704acb3e9: net/http: TLS handshake timeout

```

　
　

* Build the gradle project(rest1) to start up Spring Boot.


```sh

docker exec -w /gradle-project/rest1 rest-container gradle build

```

　
　
 
* Confirm built project and start up Spring Boot app using java command with app-profile of docker.


```sh

docker exec -w /gradle-project/rest1/build/libs rest-container ls
　rest1-0.0.1-SNAPSHOT.jar

docker exec -w /gradle-project/rest1/build/libs rest-container java -jar rest1-0.0.1-SNAPSHOT.jar --spring.profiles.active=docker &

```

　
　

* Down docker container when you end to use this app.


```sh

docker-compose down

```

　
　

* If you want to use this app again, Input commands below


```sh

docker-compose up -d
docker exec -w /gradle-project/rest1/build/libs rest-container java -jar rest1-0.0.1-SNAPSHOT.jar --spring.profiles.active=docker &

```

　
　

## Operation test

  
●Step1
　
Firstly, please insert datas required to this app from api  
＊When you restart this app, this instruction should be skipped.  
　

```sh

curl -i -X POST http://localhost:8080/rest/import/postdata

HTTP/1.1 201 
Content-Type: text/plain;charset=UTF-8
Content-Length: 21
Date: Sat, 03 Oct 2020 14:04:46 GMT

Created new post data

```

　
　
　

●Step2
　
Get specific URL lists.  
　

```sh

curl -i http://localhost:8080/get1/list

HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sun, 04 Oct 2020 14:58:53 GMT

[
	{
		"uri": "http://xxx.test1"
	},
	{
		"uri": "http://xxx.test2"
	},
	{
		"uri": "http://xxx.test3"
	},
	{
		"uri": "http://xxx.test4"
	},
	{
		"uri": "http://xxx.test5"
	}
]

```

　
　

●Step3  
　
Add request datas to database.  
If you request the url path of same number, response to status code 200(Response OK)  
　

```sh

curl -i -X POST -H 'Content-Type: plain/text' -d "2010-10-10T10:10:00Z, GET /list, 200" http://localhost:8080/post1/post/1

HTTP/1.1 201 
Location: http://localhost:8080/post1/getOne/1
Content-Type: text/plain;charset=UTF-8
Content-Length: 30
Date: Sun, 04 Oct 2020 15:01:34 GMT

2010-10-10T10:10 GET /list 200





curl -i -X POST -H 'Content-Type: plain/text' -d "2019-10-10T10:10:00Z, GET /list, 201" http://localhost:8080/post1/post/2

HTTP/1.1 201 
Location: http://localhost:8080/post1/getOne/2
Content-Type: text/plain;charset=UTF-8
Content-Length: 30
Date: Sun, 04 Oct 2020 15:02:17 GMT

2019-10-10T10:10 GET /list 201



curl -i -X POST -H 'Content-Type: plain/text' -d "2019-03-10T10:10:00Z, GET /list, 200" http://localhost:8080/post1/post/1

HTTP/1.1 200 
Content-Type: text/plain;charset=UTF-8
Content-Length: 30
Date: Sun, 04 Oct 2020 15:05:03 GMT

2019-03-10T10:10 GET /list 200



curl -i http://localhost:8080/post1/getList

["2019-03-10T10:10 GET /list 200","2019-10-10T10:10 GET /list 201"]



curl -i http://localhost:8080/post1/getOne/1

2019-03-10T10:10 GET /list 200

```

　
　

●Step3
　
Test http method OPTIONS  
　

```sh

curl -i -X OPTIONS http://localhost:8080/list

HTTP/1.1 200 
Allow: GET,HEAD,POST
Content-Length: 0
Date: Sun, 04 Oct 2020 15:07:49 GMT


curl -i  -X OPTIONS http://localhost:8080/list/item1

HTTP/1.1 200 
Allow: GET,HEAD,PUT,DELETE
Content-Length: 0
Date: Sun, 04 Oct 2020 15:08:17 GMT

```

　
　

●Step4  
　
Get Japanese prefecture information   
　
　
By post code  
　

```sh

curl -i http://localhost:8080/postcode/1650026

HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sun, 04 Oct 2020 15:10:31 GMT

{
	"zipcode": "1650026",
	"address": {
		"prefecture": "東京都",
		"city": "中野区",
		"town": "新井"
	},
	"yomi": {
		"prefecture": "ﾄｳｷｮｳﾄ",
		"city": "ﾅｶﾉｸ",
		"town": "ｱﾗｲ"
	}
}

```

　
　
By Prefecture information.  
　

・request url path : http://localhost:8080/address/{prefecture} 
　
　 
http://localhost:8080/address/東京都  
→(Base64 encode)  
http://localhost:8080/address/%E6%9D%B1%E4%BA%AC%E9%83%BD   
　

```sh

curl  -G -i http://localhost:8080/address/%E6%9D%B1%E4%BA%AC%E9%83%BD

{
	"prefecture": "東京都",
	"results": [
		{
			"city": "あきる野市",
			"cityKana": "ｱｷﾙﾉｼ",
			"link": "http://localhost:8080/address/東京都/あきる野市"
		},
		{
			"city": "三宅島三宅村",
			"cityKana": "ﾐﾔｹｼﾞﾏﾐﾔｹﾑﾗ",
			"link": "http://localhost:8080/address/東京都/三宅島三宅村"
		},
　　　・・・　
　　　・・・
　　　・・・
           ]
}

```

　
　
　
・request url path : http://localhost:8080/address/{prefecture}/{city}  
　
　
　
http://localhost:8080/address/東京都/豊島区   
→(Base64 encode)   
http://localhost:8080/address/%E6%9D%B1%E4%BA%AC%E9%83%BD   
　

```sh

curl  -G -i http://localhost:8080/address/%E6%9D%B1%E4%BA%AC%E9%83%BD/%E8%B1%8A%E5%B3%B6%E5%8C%BA

{
	"prefecture": "東京都",
	"city": "豊島区",
	"results": [
		{
			"town": "",
			"townKana": "ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱｲ",
			"link": "http://localhost:8080/address/東京都/豊島区/"
		},
		{
			"town": "上池袋",
			"townKana": "ｶﾐｲｹﾌﾞｸﾛ",
			"link": "http://localhost:8080/address/東京都/豊島区/上池袋"
		},
　　　・・・　
　　　・・・
　　　・・・
           ]
}

```

　
　
　
・request url path : http://localhost:8080/address/{prefecture}/{city}/{town}  
　
　
　
http://localhost:8080/address/東京都/豊島区/東池袋サンシャイン６０（６０階）  
→(Base64 encode)   
http://localhost:8080/address/%E6%9D%B1%E4%BA%AC%E9%83%BD/%E8%B1%8A%E5%B3%B6%E5%8C%BA/%E6%9D%B1%E6%B1%A0%E8%A2%8B%E3%82%B5%E3%83%B3%E3%82%B7%E3%83%A3%E3%82%A4%E3%83%B3%EF%BC%96%EF%BC%90%EF%BC%88%EF%BC%96%EF%BC%90%E9%9A%8E%EF%BC%89  
　

```sh

curl  -G -i http://localhost:8080/address/%E6%9D%B1%E4%BA%AC%E9%83%BD/%E8%B1%8A%E5%B3%B6%E5%8C%BA/%E6%9D%B1%E6%B1%A0%E8%A2%8B%E3%82%B5%E3%83%B3%E3%82%B7%E3%83%A3%E3%82%A4%E3%83%B3%EF%BC%96%EF%BC%90%EF%BC%88%EF%BC%96%EF%BC%90%E9%9A%8E%EF%BC%89

{
	"zipcode": "1706060",
	"address": {
		"prefecture": "東京都",
		"city": "豊島区",
		"town": "東池袋サンシャイン６０（６０階）"
	},
	"yomi": {
		"prefecture": "ﾄｳｷｮｳﾄ",
		"city": "ﾄｼﾏｸ",
		"town": "ﾋｶﾞｼｲｹﾌﾞｸﾛｻﾝｼｬｲﾝ60(60ｶｲ)"
	}
}

```

　
　

Add a prefecture information
　

```sh

"Content-Type: application/json" http://localhost:8080/postcode -d '{"zipcode": "9999999", "address": {"prefecture": "XX県", "city": "YY市", "town": "ZZ町"}, "yomi": {"prefecture": "エックスエックスケン", "city": "ワイワイシ",  "town": "ゼットゼットチョウ"}}'

HTTP/1.1 201 
Location: http://localhost/postcode/9999999
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sun, 04 Oct 2020 15:24:23 GMT

{
	"zipcode": "9999999",
	"address": {
		"prefecture": "XX県",
		"city": "YY市",
		"town": "ZZ町"
	},
	"yomi": {
		"prefecture": "エックスエックスケン",
		"city": "ワイワイシ",
		"town": "ゼットゼットチョウ"
	}
}



curl -i http://localhost:8080/postcode/9999999

HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sun, 04 Oct 2020 15:25:20 GMT

{
	"zipcode": "9999999",
	"address": {
		"prefecture": "XX県",
		"city": "YY市",
		"town": "ZZ町"
	},
	"yomi": {
		"prefecture": "エックスエックスケン",
		"city": "ワイワイシ",
		"town": "ゼットゼットチョウ"
	}
}

```

　
　
