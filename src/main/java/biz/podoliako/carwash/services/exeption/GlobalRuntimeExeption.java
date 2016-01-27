package biz.podoliako.carwash.services.exeption;


public class GlobalRuntimeExeption extends RuntimeException{
    private String msg;

    public GlobalRuntimeExeption(String message){
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }
}
