function fn(){
    karate.configure('connectTimeout', 10000);
    karate.configure('readTimeout', 10000);
    karate.configure ('ssl', true);
    return {
        aws:{
            region:"us-east-1"
        }
    };
}