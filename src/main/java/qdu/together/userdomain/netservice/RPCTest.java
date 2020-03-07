package qdu.together.userdomain.netservice;
import qdu.together.togethercore.rpc.*;

@RPC
public class RPCTest{
    @RPCMethod(MethodName = "Test")
    public Param2 Add(String a ,String b){
        return new Param2(a+b);
    }
}