package com.skplanet.bob.repository

import com.skplanet.bob.model.Restaurant
import org.springframework.data.geo.Box
import org.springframework.data.geo.Circle
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import javax.annotation.PostConstruct

@Repository
class RestaurantRepository(private val template: ReactiveMongoTemplate) {

    companion object {
        val initRestaurants = listOf(
                Restaurant(null, "동해루", null, null, Restaurant.Location("Point", listOf<Double>(127.0872612, 37.5902306))),
                Restaurant(null, "퓨전차이나", null, null, Restaurant.Location("Point", listOf<Double>(127.0780217, 37.5898317))),
                Restaurant(null, "신성묵과자점", null, null, Restaurant.Location("Point", listOf<Double>(127.0803384, 37.5868935))),
                Restaurant(null, "레스쁘아 과자점", null, null, Restaurant.Location("Point", listOf<Double>(127.0767472, 37.5885999))),
                Restaurant(null, "헬로우돈까스", null, null, Restaurant.Location("Point", listOf<Double>(127.0772463, 37.5887219))),
                Restaurant(null, "미스터홍", null, null, Restaurant.Location("Point", listOf<Double>(127.0782161, 37.5900251))),
                Restaurant(null, "중화루", null, null, Restaurant.Location("Point", listOf<Double>(127.0807776, 37.5869951))),
                Restaurant(null, "신천아구찜.탕", null, null, Restaurant.Location("Point", listOf<Double>(127.0897466, 37.5838956))),
                Restaurant(null, "진흥각", null, null, Restaurant.Location("Point", listOf<Double>(127.0867387, 37.5817405))),
                Restaurant(null, "봉구스밥버거 서일대점", null, null, Restaurant.Location("Point", listOf<Double>(127.0953276, 37.5862200))),
                Restaurant(null, "우리집밥상", null, null, Restaurant.Location("Point", listOf<Double>(127.0856016, 37.5757506))),
                Restaurant(null, "뚜레쥬르 용마산역점", null, null, Restaurant.Location("Point", listOf<Double>(127.0850439, 37.5748858))),
                Restaurant(null, "왕짜장", null, null, Restaurant.Location("Point", listOf<Double>(127.0853664, 37.5759146))),
                Restaurant(null, "용용떡볶이", null, null, Restaurant.Location("Point", listOf<Double>(127.0862922, 37.5747369))),
                Restaurant(null, "꾼떡 면목점", null, null, Restaurant.Location("Point", listOf<Double>(127.0827027, 37.5746099))),
                Restaurant(null, "김밥사랑", null, null, Restaurant.Location("Point", listOf<Double>(127.0827027, 37.5746099))),
                Restaurant(null, "안녕 마카롱", null, null, Restaurant.Location("Point", listOf<Double>(127.0879493, 37.578935))),
                Restaurant(null, "태화루", null, null, Restaurant.Location("Point", listOf<Double>(127.0871143, 37.6019718))),
                Restaurant(null, "이가김밥", null, null, Restaurant.Location("Point", listOf<Double>(127.0947693, 37.5988492))),
                Restaurant(null, "황금성", null, null, Restaurant.Location("Point", listOf<Double>(127.0837246, 37.5922864))),
                Restaurant(null, "화풍", null, null, Restaurant.Location("Point", listOf<Double>(127.0881262, 37.5932071))),
                Restaurant(null, "김밥천국", null, null, Restaurant.Location("Point", listOf<Double>(127.0765595, 37.5926243))),
                Restaurant(null, "한스델리", null, null, Restaurant.Location("Point", listOf<Double>(127.0764153, 37.5932016))),
                Restaurant(null, "정원반점", null, null, Restaurant.Location("Point", listOf<Double>(127.078943, 37.5932746))),
                Restaurant(null, "최가네즉석김밥", null, null, Restaurant.Location("Point", listOf<Double>(127.0792686, 37.6042517))),
                Restaurant(null, "봉구스밥버거 중화점", null, null, Restaurant.Location("Point", listOf<Double>(127.0805004, 37.6017042))),
                Restaurant(null, "파파존스피자 중화점", null, null, Restaurant.Location("Point", listOf<Double>(127.0791274, 37.6051364))),
                Restaurant(null, "낙원사랑분식", null, null, Restaurant.Location("Point", listOf<Double>(127.0799336, 37.6043245))),
                Restaurant(null, "곰분식", null, null, Restaurant.Location("Point", listOf<Double>(127.0785809, 37.5992231))),
                Restaurant(null, "황궁짜장", null, null, Restaurant.Location("Point", listOf<Double>(127.0759881, 37.6010067))),
                Restaurant(null, "김밥천국", null, null, Restaurant.Location("Point", listOf<Double>(127.0796001, 37.5992229))),
                Restaurant(null, "파리바게뜨 중화역점", null, null, Restaurant.Location("Point", listOf<Double>(127.0791176, 37.6013429))),
                Restaurant(null, "중화요리하이난", null, null, Restaurant.Location("Point", listOf<Double>(127.075936, 37.5988168))),
                Restaurant(null, "김밥천국", null, null, Restaurant.Location("Point", listOf<Double>(127.0777735, 37.5979355))),
                Restaurant(null, "하루미", null, null, Restaurant.Location("Point", listOf<Double>(127.0749298, 37.5939234))),
                Restaurant(null, "꽃돼지 수제돈까스", null, null, Restaurant.Location("Point", listOf<Double>(127.082954, 37.6178924))),
                Restaurant(null, "피자스쿨(묵동점)", null, null, Restaurant.Location("Point", listOf<Double>(127.0793614, 37.6118869))),
                Restaurant(null, "버무리 떡볶이", null, null, Restaurant.Location("Point", listOf<Double>(127.083455, 37.617034))),
                Restaurant(null, "유유김밥", null, null, Restaurant.Location("Point", listOf<Double>(127.1078182, 37.6024815))),
                Restaurant(null, "중국관", null, null, Restaurant.Location("Point", listOf<Double>(127.1021209, 37.5993468))),
                Restaurant(null, "금용각", null, null, Restaurant.Location("Point", listOf<Double>(127.1022311, 37.5946956))),
                Restaurant(null, "바른생활샌드위치", null, null, Restaurant.Location("Point", listOf<Double>(127.1031929, 37.6022113))),
                Restaurant(null, "피자스쿨 망우우림시장점", null, null, Restaurant.Location("Point", listOf<Double>(127.0993966, 37.5956471))),
                Restaurant(null, "김밥천국", null, null, Restaurant.Location("Point", listOf<Double>(127.1002367, 37.5950066))),
                Restaurant(null, "동해원", null, null, Restaurant.Location("Point", listOf<Double>(127.0997183, 37.5910345))),
                Restaurant(null, "샬롬 중화요리", null, null, Restaurant.Location("Point", listOf<Double>(127.097064, 37.5916044))),
                Restaurant(null, "경보장", null, null, Restaurant.Location("Point", listOf<Double>(127.0983873, 37.5903242))),
                Restaurant(null, "춘향이와 흥부", null, null, Restaurant.Location("Point", listOf<Double>(127.0993536, 37.595272))),
                Restaurant(null, "얌샘김밥", null, null, Restaurant.Location("Point", listOf<Double>(127.0980801, 37.6056449))),
                Restaurant(null, "중화요리락궁", null, null, Restaurant.Location("Point", listOf<Double>(127.0959119, 37.6046561))),
                Restaurant(null, "밀로베이커리", null, null, Restaurant.Location("Point", listOf<Double>(127.0964728, 37.6021096))),
                Restaurant(null, "파리바게트 신내제일점", null, null, Restaurant.Location("Point", listOf<Double>(127.0965139, 37.6054527))),
                Restaurant(null, "죠스떡볶이", null, null, Restaurant.Location("Point", listOf<Double>(127.0955549, 37.6066393))),
                Restaurant(null, "세븐돈까스", null, null, Restaurant.Location("Point", listOf<Double>(127.1002309, 37.6006588))),
                Restaurant(null, "김밥천국", null, null, Restaurant.Location("Point", listOf<Double>(127.0946811, 37.6064082))),
                Restaurant(null, "파리바게뜨 신내중앙점", null, null, Restaurant.Location("Point", listOf<Double>(127.0946811, 37.6064082))),
                Restaurant(null, "봉구스밥버거 봉화산역점", null, null, Restaurant.Location("Point", listOf<Double>(127.0910356, 37.61410212))),
                Restaurant(null, "봉구스밥버거 중랑구청점", null, null, Restaurant.Location("Point", listOf<Double>(127.094681, 37.6047395))),
                Restaurant(null, "왕서방", null, null, Restaurant.Location("Point", listOf<Double>(127.0848091, 37.5981353))),
                Restaurant(null, "씨유(CU) 가능공원점", null, null, Restaurant.Location("Point", listOf<Double>(127.0325694, 37.7467369))),
                Restaurant(null, "씨유(CU) 가능제일점", null, null, Restaurant.Location("Point", listOf<Double>(127.0417476, 37.7477260))),
                Restaurant(null, "씨유(CU) 경민대점", null, null, Restaurant.Location("Point", listOf<Double>(127.0273286, 37.7430965704))),
                Restaurant(null, "씨유(CU) 금오중앙점", null, null, Restaurant.Location("Point", listOf<Double>(127.0603026, 37.7512578))),
                Restaurant(null, "씨유(CU) 녹양현대점", null, null, Restaurant.Location("Point", listOf<Double>(127.0386792800, 37.7586082))),
                Restaurant(null, "씨유(CU) 민락주공점", null, null, Restaurant.Location("Point", listOf<Double>(127.0887589, 37.7456067))),
                Restaurant(null, "씨유(CU) 민락중앙점", null, null, Restaurant.Location("Point", listOf<Double>(127.0919937, 37.7407250))),
                Restaurant(null, "씨유(CU) 민락청구점", null, null, Restaurant.Location("Point", listOf<Double>(127.0930973377, 37.7377724827))),
                Restaurant(null, "씨유(CU) 상록아이파크점", null, null, Restaurant.Location("Point", listOf<Double>(127.0762084, 37.7519910))),
                Restaurant(null, "씨유(CU) 신곡서해점", null, null, Restaurant.Location("Point", listOf<Double>(127.0616639, 37.7290396))),
                Restaurant(null, "씨유(CU) 신곡중앙점", null, null, Restaurant.Location("Point", listOf<Double>(127.0611129, 37.7404206153))),
                Restaurant(null, "씨유(CU) 신촌교차로점", null, null, Restaurant.Location("Point", listOf<Double>(127.0342832, 37.7505101))),
                Restaurant(null, "씨유(CU) 신한대학점", null, null, Restaurant.Location("Point", listOf<Double>(127.0470817328, 37.7088534193))),
                Restaurant(null, "씨유(CU) 용현건영점", null, null, Restaurant.Location("Point", listOf<Double>(127.0794074701, 37.7347552414))),
                Restaurant(null, "씨유(CU) 용현주공점", null, null, Restaurant.Location("Point", listOf<Double>(127.0816079077, 37.7343825390))),
                Restaurant(null, "씨유(CU) 의정부광장점", null, null, Restaurant.Location("Point", listOf<Double>(127.0377061154, 37.7373022761))),
                Restaurant(null, "씨유(CU) 의정부금오점", null, null, Restaurant.Location("Point", listOf<Double>(127.0726151439, 37.7495138875))),
                Restaurant(null, "씨유(CU) 의정부녹양점", null, null, Restaurant.Location("Point", listOf<Double>(127.0371897441, 37.7573579926))),
                Restaurant(null, "씨유(CU) 의정부문화점", null, null, Restaurant.Location("Point", listOf<Double>(127.0461626, 37.7468835))),
                Restaurant(null, "씨유(CU) 의정부미르점", null, null, Restaurant.Location("Point", listOf<Double>(127.0806444, 37.7500340))),
                Restaurant(null, "씨유(CU) 의정부백석점", null, null, Restaurant.Location("Point", listOf<Double>(127.0399992970, 37.7316543))),
                Restaurant(null, "씨유(CU) 의정부보광점", null, null, Restaurant.Location("Point", listOf<Double>(127.0780287, 37.7359078))),
                Restaurant(null, "씨유(CU) 의정부산들점", null, null, Restaurant.Location("Point", listOf<Double>(127.0971154, 37.7416337))),
                Restaurant(null, "씨유(CU) 의정부서부점", null, null, Restaurant.Location("Point", listOf<Double>(127.0395263, 37.7391447))),
                Restaurant(null, "씨유(CU) 의정부서영점", null, null, Restaurant.Location("Point", listOf<Double>(127.0451017382, 37.7127247767))),
                Restaurant(null, "씨유(CU) 의정부송산점", null, null, Restaurant.Location("Point", listOf<Double>(127.0870629638, 37.7426567427))),
                Restaurant(null, "씨유(CU) 의정부승기점", null, null, Restaurant.Location("Point", listOf<Double>(127.0491799, 37.7262773))),
                Restaurant(null, "씨유(CU) 의정부시청", null, null, Restaurant.Location("Point", listOf<Double>(127.0374234688, 37.7347226))),
                Restaurant(null, "씨유(CU) 의정부신곡", null, null, Restaurant.Location("Point", listOf<Double>(127.0574655, 37.7431443))),
                Restaurant(null, "씨유(CU) 의정부신명점", null, null, Restaurant.Location("Point", listOf<Double>(127.0617624426, 37.7433322240))),
                Restaurant(null, "씨유(CU) 의정부신한대점", null, null, Restaurant.Location("Point", listOf<Double>(127.0482928596, 37.7087736437))),
                Restaurant(null, "씨유(CU) 의정부역점", null, null, Restaurant.Location("Point", listOf<Double>(127.0439963, 37.7363150))),
                Restaurant(null, "씨유(CU) 의정부운동장점", null, null, Restaurant.Location("Point", listOf<Double>(127.0302380, 37.7555301))),
                Restaurant(null, "씨유(CU) 의정부이롬점", null, null, Restaurant.Location("Point", listOf<Double>(127.0744525381, 37.7577836192))),
                Restaurant(null, "씨유(CU) 의정부익봉점", null, null, Restaurant.Location("Point", listOf<Double>(127.0574085, 37.7314022))),
                Restaurant(null, "씨유(CU) 의정부충신점", null, null, Restaurant.Location("Point", listOf<Double>(127.048855, 37.731513))),
                Restaurant(null, "씨유(CU) 의정부평화점", null, null, Restaurant.Location("Point", listOf<Double>(127.0867246921, 37.7366781606))),
                Restaurant(null, "씨유(CU) 의정부한전점", null, null, Restaurant.Location("Point", listOf<Double>(127.0514075286, 37.7456544861))),
                Restaurant(null, "씨유(CU) 의정부행복점", null, null, Restaurant.Location("Point", listOf<Double>(127.0461360159, 37.7419158452))),
                Restaurant(null, "씨유(CU) 의정부호원점", null, null, Restaurant.Location("Point", listOf<Double>(127.0474857429, 37.7164998)))
        )
    }

//    @PostConstruct
    fun init() =
            initRestaurants.map(Restaurant::toMono).map(this::create).map(Mono<Restaurant>::subscribe)

    fun create(restaurant: Mono<Restaurant>) = template.save(restaurant)
    fun findById(id: String) = template.findById<Restaurant>(id)
    fun findByName(name: String): Mono<Restaurant> {
        val query = Query()
        query.addCriteria(Criteria.where("name").`is`(name))
        return template.findOne(query, Restaurant::class.java)
    }

    fun getCountGeoWithin(latitude: Double, longitude: Double, distance: Double): Mono<Long> {
        val query = Query()
        val circle = Circle(latitude, longitude, distance / 6378.1)
        query.addCriteria(Criteria.where("location").`withinSphere`(circle))
        return template.count(query, "Restaurants")
    }

    fun getGeoWithinBySquare(bl: Point, tr: Point): Flux<Restaurant> {
        val query = Query()
        val box = Box(bl, tr)
        query.addCriteria(Criteria.where("location").`within`(box))
        return template.find(query, Restaurant::class.java)
    }

    fun getCountGeoWithinBySquare(bl: Point, tr: Point): Mono<Long> {
        val query = Query()
        val box = Box(bl, tr)
        query.addCriteria(Criteria.where("location").`within`(box))
        return template.count(query, "Restaurants")
    }

}

