
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    private static String ISSUER = "AUTH_CENTER";
    /****
     * 创建Jwt令牌
     */
    @Test
    public void testCreateJwt1(){
        try {
            Algorithm algorithm = Algorithm.HMAC256("UixD3r2xs2RLcY00TU5vfvnBXRUnqrkh");
            String token = JWT.create()
                    .withClaim("username","username")
                    .withClaim("userId","userId")
                    .withClaim("orgId","orgId")
                    .withClaim("appType","appType")
                    .withClaim("clientId","clientId")
                    .withClaim("tokenType","tokenType")
                    .withClaim("userType","userType")
                    .withIssuer(ISSUER)
                    .withIssuedAt(new Date())
                    .sign(algorithm);
            System.out.println("token:"+token);
        } catch (Exception exception){
            System.out.println("wrong");
        }
    }
    @Test
    public void testCreateJwt2(){

        JwtBuilder builder= Jwts.builder()
                .setId("888")             //设置唯一编号
                .setSubject("啦啦啦")       //设置主题  可以是JSON数据
                .setIssuedAt(new Date())  //设置签发日期
                //参数1：签名算法，参数2：秘钥（盐）
                .signWith(SignatureAlgorithm.HS256,"maltose");//设置签名 使用HS256算法，并设置SecretKey(字符串)
        //获取密文 并返回一个字符串
        System.out.println( builder.compact() );
    }
    @Test
    public void createJwt3(){
        long currentTimeMillis = System.currentTimeMillis();
        long l = currentTimeMillis + 200000;
        //
        //创建jwtbuilder
        JwtBuilder builder = Jwts.builder();
        //1.头(不设置,默认) 2 载荷(数据) 3. 签名
        builder.setId("唯一的标识")
                .setIssuer("颁发者")//生成令牌的一方
                .setSubject("主题")//就是描述 jwt的信息
                .setExpiration(new Date(l))//设置过期时间
                .signWith(SignatureAlgorithm.HS256,"maltose");//设置签名

        //3.可以自定义载荷
        Map<String, Object> map = new HashMap<>();
        map.put("myaddress","cn");
        map.put("mycity","sz");
        builder.addClaims(map);

        //生成令牌
        String compact = builder.compact();
        System.out.println(compact);

    }
    @Test
    public void testParseJwt3(){
        String compactJwt="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiLllK_kuIDnmoTmoIfor4YiLCJpc3MiOiLpooHlj5HogIUiLCJzdWIiOiLkuLvpopgiLCJleHAiOjE2MTc2MzMwNjUsIm15Y2l0eSI6InN6IiwibXlhZGRyZXNzIjoiY24ifQ.u6oe0PGev_drYKqW4-CzZ9HdmR8BjrfWr_h7MgZ8kv4";
        Claims claims = Jwts.parser().
                setSigningKey("maltose").//秘钥（盐）
                parseClaimsJws(compactJwt).
                getBody();
        System.out.println(claims);
    }
    @Test
    public void testParseJwt1(){
        try{
            DecodedJWT jwt = JWT.decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGllbnRJZCI6ImNsaWVudElkIiwiYXBwVHlwZSI6ImFwcFR5cGUiLCJpc3MiOiJBVVRIX0NFTlRFUiIsInVzZXJUeXBlIjoidXNlclR5cGUiLCJ0b2tlblR5cGUiOiJ0b2tlblR5cGUiLCJ1c2VySWQiOiJ1c2VySWQiLCJpYXQiOjE2MTc2MzE0OTksIm9yZ0lkIjoib3JnSWQiLCJ1c2VybmFtZSI6InVzZXJuYW1lIn0.VnENXoxB9R4jhzz2OkbvZxZklwwWRwcbF0CGqHpSmtg");
            Map<String, Claim> claimMap = jwt.getClaims();
            String username = getString(claimMap.get("username"));
            String userId = claimMap.get("userId").asString();
            String orgId = getString(claimMap.get("orgId"));
            int appType = claimMap.get("appType").asInt();
            String clientId = claimMap.get("clientId").asString();
            String tokenType = claimMap.get("tokenType").asString();
            int userType = claimMap.get("userType").asInt();

//            JwtToken jwtToken = null;
//            if(ACCESS_TOKEN.equals(tokenType)){
//                jwtToken = new JwtAccessToken(username,orgId,userId,appType,clientId,userType);
//            }else if(REFRESH_TOKEN.equals(tokenType)){
//                jwtToken = new JwtRefreshToken(username,userId,appType,clientId,userType, orgId);
//            }
        }catch (Exception e){
            System.out.println( "jwt处理异常:" );

        }
    }

    @Test
    public void testParseJwt2(){
        String compactJwt="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLllabllabllaYiLCJpYXQiOjE2MTc2MzEyNzJ9.X7L6GiS0mfCUq82LmxOuY19qm2MRxvgiZXXHs5rle1Y";
        Claims claims = Jwts.parser().
                setSigningKey("maltose").//秘钥（盐）
                parseClaimsJws(compactJwt).
                getBody();
        System.out.println(claims);
    }
    private static String getString(Claim claim){
        if(claim == null){
            return null;
        }
        return claim.asString();
    }
}
