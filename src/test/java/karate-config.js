function fn(){
    karate.configure('connectTimeout', 1000);
    karate.configure('readTimeout', 1000);
    karate.configure ('ssl', true);
    return {
        aws:{
            region:"us-east-1"
        }
    };
}