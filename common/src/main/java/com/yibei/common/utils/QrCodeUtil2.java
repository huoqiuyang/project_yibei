package com.yibei.common.utils;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
 
public class QrCodeUtil2 {

    /**
     * 生成带手机号参数小程序二维码
     */
    public static String getPhoneBaseQrCode(String phone){
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        try {
            String accessToken = WeChatApiUtil.getAccessToken();
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
            Map<String,Object> param = new HashMap<>();
            param.put("page", "pages/index/index");
            param.put("width", 430);
            param.put("scene", phone);
            param.put("auto_color", false);
            Map<String,Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            @SuppressWarnings({ "rawtypes", "unchecked" })
            HttpEntity requestEntity = new HttpEntity(param, headers);
            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            byte[] result = entity.getBody();
            String str = Base64.encodeBase64String(result);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        String phoneQrCode = getPhoneQrCode("张春林","18523428758");
        System.out.println(phoneQrCode);
    }

    /**
     * 生成带手机号参数小程序二维码
     */
    public static String getPhoneQrCode(String name,String phone){
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        try {
            String URL = "https://api.weixin.qq.com/cgi-bin/token?appid=wx26e3911a3c12cc5b&secret=581c092a8c35be5f4f9e298048dcf708&grant_type=client_credential";
            JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
            if(jsonObject.containsKey("access_token")){
                String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+jsonObject.getString("access_token");
                Map<String,Object> param = new HashMap<>();
                param.put("page", "pages/index/index");
                param.put("width", 430);
                param.put("scene", phone);
                param.put("auto_color", false);
                Map<String,Object> line_color = new HashMap<>();
                line_color.put("r", 0);
                line_color.put("g", 0);
                line_color.put("b", 0);
                param.put("line_color", line_color);
                MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
                @SuppressWarnings({ "rawtypes", "unchecked" })
                HttpEntity requestEntity = new HttpEntity(param, headers);
                ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
                byte[] result = entity.getBody();
                String str = new BASE64Encoder().encode(result);

                String image = getImage(str, name, phone);

//            OutputStream os = new FileOutputStream("F:/新建文件夹/text.png");
//            os.write(result, 0, result.length);
//            os.flush();
//            os.close();
                return image;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getImage(String base64,String name,String phone){
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes;
        byte[] bytes2;
        Image er=null;
        Image er2=null;
        ByteArrayInputStream bais = null;
        ByteArrayInputStream bais2 = null;
        try {
            bytes = decoder.decodeBuffer(base64);
            bytes2 = decoder.decodeBuffer("iVBORw0KGgoAAAANSUhEUgAAAM0AAADNCAYAAAAbvPRpAAAACXBIWXMAAAsTAAALEwEAmpwYAAAGh2lUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDIyLTAzLTEwVDEwOjA3OjAxKzA4OjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAyMi0wMy0xMFQxMDoxMiswODowMCIgeG1wOk1ldGFkYXRhRGF0ZT0iMjAyMi0wMy0xMFQxMDoxMiswODowMCIgZGM6Zm9ybWF0PSJpbWFnZS9wbmciIHBob3Rvc2hvcDpDb2xvck1vZGU9IjMiIHBob3Rvc2hvcDpJQ0NQcm9maWxlPSJzUkdCIElFQzYxOTY2LTIuMSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDpjNzIyNWRjMC0yMzVlLThhNGEtOTZlZi1jMWIyNDc4MWJkNDkiIHhtcE1NOkRvY3VtZW50SUQ9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDoxNzgyZmY1MC1jMjAwLTcyNGYtYjFkNy1jMGI4ZjhhNDU4OWIiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDpjNTc4ZjExZC1hZThlLWI3NDktYjBlYy0xMmM1Yjg2Y2MwYTEiPiA8cGhvdG9zaG9wOlRleHRMYXllcnM+IDxyZGY6QmFnPiA8cmRmOmxpIHBob3Rvc2hvcDpMYXllck5hbWU9Ind3dy55enl3OC5jb20iIHBob3Rvc2hvcDpMYXllclRleHQ9Ind3dy55enl3OC5jb20iLz4gPC9yZGY6QmFnPiA8L3Bob3Rvc2hvcDpUZXh0TGF5ZXJzPiA8eG1wTU06SGlzdG9yeT4gPHJkZjpTZXE+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJjcmVhdGVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOmM1NzhmMTFkLWFlOGUtYjc0OS1iMGVjLTEyYzViODZjYzBhMSIgc3RFdnQ6d2hlbj0iMjAyMi0wMy0xMFQxMDowNzowMSswODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDpjNzIyNWRjMC0yMzVlLThhNGEtOTZlZi1jMWIyNDc4MWJkNDkiIHN0RXZ0OndoZW49IjIwMjItMDMtMTBUMTA6MTIrMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz5VvKMYAABWzUlEQVR4nO29eXRc533f/XnuLJjBDgIkgAE3gKIIgJQokhIXUSJF2aIoOrLU2Jb6VlGa+m0dJ2mSuqenPklP0rR9m540OXXi1FstL4ksp7IlRzQViotMivsmgjsIiiRAAuAAJHYMMDOYmXuf94+7YGYwgxkAg43C95wRhZl7n/vce5/v81uf3yOklMwmRCIR7HZ7xts1n4MQItNNlwOLgArj/8uABUAJMA8oBPKBHCAbyAIcgAJoQBgYAvzAINAP9ALdQCdwH2gH2oC7QIvx/+OGlDLt5xD/PsZ6bnt7O2VlZWN+p5M1DtLB9Fz1wcRCoAZYASwHHgKWAZWAc5xtKugkykInVgySDNAQ0ATcAm4CN4DrwDWgdZz9mEMU5kgzPmQBa4zPo8ZnFQkG9mQimjBRBHKiE3dF3OH9wBXgkvE5b3yGpqKvDxLmSJM+1gAbgPVSyieEEKsm0thY1Jh0zk2jrXzgSeNj4gpwFjgDnEYn0RxSYI40yeECtgBPAZvRB5sLMmP3pNNGMmJN5Ppxba4yPv8KCAIngOPAMeCI8d0c4jBHmlg4gGeBbcAzwIaJSISJItPXTXEvLvR7f9Y45jTwEXAIOIjukJgDc6Qx8TiwXQixHdga/UMmBu50Ei8a8X1I0a8NxufrwGFgv/H5eBK7OCvwaSZNIbDT+OwQQhRP1oVmAmESYQz92mp8/j2wF9hjfHonpWMzHGKicZqZOiCSQUpZDXweeBHdXplShEIhOjs7ASgpKcHpTO2N9vl8XLt2DYD169fT1dXF0NAQeXl55OXlTWp/R8ExYHckEvmlw+FomK5OTBTjGf+fGkkjpVwP/DrwMiPdsRmDSYqOjg6Ghoa4c+cOAI2NjQDs2LGD+/fvU1dXh8fj4dVXX01JnL1799LY2MimTZsAuHXrFocOHcLj8fD6669P1q2kwlPAU3a7/ctSyveAXwghzkxXZ6YSDzxppJRPAV8EvoAegMyojREKhbhw4QJ37tyhp6eHYDBIeXk5paWluFwuNm/ezK9+9SsA1q5dy+rVqwmFQrS3t+P1ejl8+DDPPfdc0vYvXrxIY2MjHo+HLVu2APDYY49x6NAhvF4vXV1dFBcn1iwbGho4cuQIRUVFPPvss0mPmyBWoNs9r0kp35VSvqMoyrHJuNBMwQNLGinlRuBV41Me/dt4CRMKhbh79y6VlZXWd6aUePzxxykpKRmhLh04cACv18vatWstcjidTl599VW++93vUldXx6JFi6iurk54vcOHDwOwc+fOmGuuXbuWuro6rl69apEpGl1dXezfv5/y8nIaGxspLCwclZwThZRyoRDiD4UQr0gp3wbeFkKcmrQLTiOU6e5ApiGlrJVS/nfgH4B/Rxxhxgufz8c3vvENDhw4MOK39evXU1lZOYIwR44csdSwrVtjnHI4nU5ee+01APbv309XV9eIdg8fPkwgEGDbtm0jpMTKlSsBXZrEIxQK8e677wLw0ksv4fF4aGpqGvX+mpqaEvYhXURNROXoz/0fpJT/XUpZO+5GZygeGEkjpZwP/AbwOnr0frRjxyxt7t69C5BQIiRCQ0MDJ0+eHNVuKS4u5qWXXmLXrl3s2bMnxj7xer0W4davXz/iXI/Hg8fjwev10tTUFCP99u3bR09PD6+88gpOp5OysjLq6urw+XwJHQder5ef/exnAGzbtm3E9Zqamjhw4AA9PT0AVFVV8fDDD1NVVTWaI2Ip8MfAC1LKN4GfCCE6RntmswUPhKSRUr4ipfwx8L9IQRjQZ8Wxek1aWloAWLRoUcpju7q62LVrF263O6WhX11dzdq1a/F6vZw5M2xHHz9+HIDPfOYzSc999NFHAbh586b1XUNDA/X19WzatMki0oIFC4Bh4sf39Z133gF0myueMD6fj5/97Ge43W5eeeUVXn/9dR5//HHu37/Pj370Iw4cOIDP5xvtcaxBfy8/llK+MtqBswWzmjRSyjVSyr8FfiSE2JnyhCiYkqahoYFQKJTyeFO9iZ7RE8Hn8/HWW2/hdrt57bXX0nIpb926FY/Hw6FDh+jq6qKhocHylnk8nqTn1dTUxPTN5/Oxf/9+ioqK2Lhxo3XcwoULAbh//37M+V1dXbz11lsEAoEYmyv+fkAnb2VlJR6Ph8rKSp577jm++tWvAvCjH/0ooZoYh53Aj6SUfyulTDmxzWTMSvVMSmkH/g3wr4G142nD5/Px3nvv4fV6cbvdvPjii0kJEQqF6OnpoaqqKmW77733HoFAgJdeeiltb5XT6WTnzp289dZb7Nmzh0AggNvtjhn4yc6rra2lvr4en8/HRx99RCAQGEHW4uJi3G635f427ykVYQBL/bp58+YIAjudTp577jkWLVrErl27gJTqazbwb4EnpZRvRCKR7zscjsioNzkDMeskTTgcXg98D/g24yQM6IPh5Zdfpra2lkAgwM9+9jN2796dUOqYwcglS5aM2uaRI0fwer1s2rRpxOAJhUJ4vV68Xm/CWbm4uJitW7fi9Xrp6elh+/btaUmpVav0ZOuPPvqI+vr6hE4DgPLycrxer9WXt99+OyVhQH9O27Zt4+TJk0mlSXV1NbW1tezfvz9lfw2sBb5tt9u/19LSMtJgm+GYVZJGSvlvgN8hDbslHeTl5fHiiy+ybt063n//ferr62lra+PXfu3XYmbV1lZ97Zap5iRCU1MTJ0+exO12s2jRIs6cOcO9e/fo7e3F6/VSVFREUVERhYWFSe0iU5J5PJ60HQ6VlZUUFRVRX1+f1GkAOuEbGxvx+XycOnVqhBs8Hg0NDVy+fNmKPQHs2rWL+/fvJ3Rxl5aWUl9fj9frHVWljMOXFy5cuCYcDn/H4XB8P92TphuzgjRSyoeA3zM+jky37/F4+K3f+i1OnTrFyZMnefPNN2O8SPfu3bOOS4RQKMTu3bsBcLlcXLlyhdLSUtatW0dWVlbaatpHH30EjG78J8JXvvKVlMfMnz8fgFOnTlleuWSEOXLkCCdPnqSqqootW7aQn6+vrevv72f//v3cu3ePl156yZKETU1NnDp1CrfbPRbCmFhjt9u/ZbimvyWEuJnyjGnGjM89k1K+APwBsCPaVTya29hUsdJRb+Lh9Xp5//33LRvmpZde4u233wYYNWXF6/WmnUuWqL+HDx+mrq4uocs3E/D5fFY6Tqr0nb/4i7+gtraWF198cdR2olFUVMQXvvCFiWYd7AW+KYT4YCKNjAXjGf8zmjRSyt8D/hB9zX1aaGhoYP/+/Xzxi18cz6wH6IPYlDpFRUX09PSwadOmhGrJeNHV1cXBgwcBaGtrw+Vy8dxzz6X0zmUCoVBoVHJ/85vfpKamZlRbx5RGbrd7Qs86AW4AfyOE+FamGhwND0zCppSyDPia8RmTOnb58mUCgcCEru90OtmyZQuLFi2y1C4z1pEpOJ1ONm/eDDDl2cqppOHGjRs5dOhQ0vQegC1btrBgwQJ27dpFa2trJkmzHPiGlHIx8A0hRHumGs4UZpz3TEr5GPBXwH9kHPZLYWFhxvpSWVlpuX1NvT5TyMvLs6L605jenxDr169n06ZN7Nq1i4sXLyY9zvSaHTp0KFWAc6xwoL//vzLGw4zCjCKNlPI54C+B18bbRkFBQeY6hO4EGKeBO6uxZcsWXnnlFU6fPs3Pf/5zy10dj3Xr1gFkmjQmXgP+0hgXMwYzRj2TUr6KnmKeEXdyKpXB1MlfeumlpCqIz+ezYh9TiZmyPLqyspKvfOUrNDQ08P777+N2u3n00Udjcs6uXr0KQFZW1mR147NAsZRynhDi7cm6yFgwI0hjxF/+CL2w3qjo6urC6XQmVWlGi6XEo7a2NilhQqEQ7733Hh6Ph8ceeyztNieKmUKYaFRXV1NdXU1TUxM3b960sq9NrF27drLW6phYA/wPKWW+EGLa4znTThop5b9Dz4adn+rYixcvsnfvXkB/URs3bkxKHjMglwzJPGFm8O/atWvU1NSwdevWcbmRH0RUVlZaeWc+n8/Kmp4im6wS+O9SyhwhxF9PxQWTYVpJI6X8OvAn6HWMU+L06dNUVVXhcrmoq6ujrq5uBHlMlcwMSI4VnZ2dLFiwYFRCZgrR7k5Tusw0KZMMEyFLQ0MD/f39gO5gqaqqSjoxxUne+cD/J6XMEkL8xbgungFMG2mklH8M/Cl6ide0UF5eTltbG1/5yldYt24dx48fjyGPKRWKiorG3a+piJOYkFISDodRFAWHI+OJDtMCs+hHInvSzHkzk2TN9xkIBNi0aRMbN24cQZ4Ek0gO8F/C4bDN4XD8+aTdyCiYFtIYhPkzxuhSNvObfD4fHo+HL33pS9YCqbq6Oq5du8b27dspLy+nvr5+UvqeSbS2tnLjxg0effRRK80FZqZdky7MSez1118fQZzDhw/j9XrZsWMHq1evtr7v6urixIkTfPe73+W1115Lxz7Kstvtf2Y8pyknzpSTxlDJ/pRxxGBMI7+zs9NSDaI9PPv377cWf01E2kwmpJSoqsrNmzfZu3cvvb29LF8el/AgBBKYjbTZuHEjCxYsGKG6+Xw+SyOIJgzoGd4vvvgiDQ0NvPXWW2zfvj2dhFUH8KdSSnWqVbUpJY1h9P8JY1DJolFSUgLoqyjj1ajq6mqqqqo4dUqv5ZBqLUqmkI5UiLZdwuEwV69e5YMPPqClpYV58+aNOF4As2vXoGHk5eWNIAUMx3HM2gaJUF1dzfz583njjTfIz89PJzaWBfyJlHJoKp0DUxbcNNzKf0wKoz9ZEA2w7JXoxVTxv2/ZsoUtW7ZMmccrUanXZAgGg5w/f55du3Zx584dVFWNPSDqVL1VTf9IJsgibSInTymKi4vZsWMH77zzTloratHH0x8b42tKMCWkMQKXf0QKt/KBAwd48803+eY3v8mRI0cSVkeprKzE6/Wm+0CnHMlI5Pf7OXbsGLt376atrQ0pZQzBrP+XcX8/IPB4PLjd7ph6BsmwevVqXC6XVb4qDcwH/sgYZ5OOSSeNkQLxdVIELn0+H8FgELfbTSAQ4OTJk7zxxhu8+eabXLx40SKJuYArUZGImYRoUvh8Pg4fPszevXvp6OiwvleU4ccvhNDFi4j6G0X/RH0/+kVJIpFmRrbU1q1buXDhQloT3he+8IWxJslWAl+fipSbSbVpjGS7/0iK1BizBJG5fsOMPF+7ds1aInz48GE2btxoFZNIZNfMJAgh0DTNWgJw5swZBgYGHjgJMhasXr2a5uZmdu3axZe+9KVRjy0uLh5PlsEa4D9KKTuEEBfG2c2UmLT1NEZ6/1+RIvnSdBd/7WtfS2iHmASqq6sDwO12A8SQbLqRyBmgaRr3799n3759fPzxxwwNDY04R1EUSkpK+O3f/m0WL16coGXDFpEKSIkU5vPWv5eGBBHRxwEI04aZGRImGmasprCwkOeff36ybM+3gP+QzrKCmbae5mukka3c29sL6AXCE7kZzdSNrVu30tjYyNmzZykrKxtRsXI6EV9HTdM0mpub2b9/P5cvXx6X/aW3J3WtLBIBKdE1NgGK7pKejT5psyTv4cOH+fGPfzyiHkOG8Br6TtdfT3VgJDL2YjiTQhpjxeXX0jn2kUceobGxkRs3bozqm3c6nVbi4ExGJBKhsbGRPXv2cPPmTcLh8Dj2yNR0gmADTQX/IL3371E4vwRyc0HaQChIdO+b1IRuH1lNGhLG5PEMI5dZ+unixYu88847lJeX8/jjjydUt8362R0dHSxcuHAsBPualLJ5MlaAZpw0xpr+PyTN4GVVVRVut5v6+vrJFNcTxmjxGFPKRCIRrl27xp49e7h9+zaapqtJY43um+1JIVGQ4Pdz/L1fUl5cyNrt22H+AoTdgbAJVKmhiJmnhqWD1atXW7G1AwcOjCgQ0tXVxbvvvmuVwwVdPd+6dWvCWFAcHMAfSikbM11zIKOkkVI+JKX8AyFE2mv6nU4nNTU11NXVJVXRZjqklIRCIerq6vjwww/xer1omjaxHZzN0zQgoiLa7nLhn35JbpuXh1/5f2DRQhB2BMKwYUxvWxRmmIRJhLy8vIS1CMxihqDv6VNTU4PP5+PWrVscPnyY5ubmdCbZ5cAfhMPhGw6HI2NVbjI9Rf2eEGLHWE8yo8Rnz57NcHcyh0SD33QrB4NBTpw4wfvvv8/du3etoGX0OWMyOIXQyQAgNUCSE47g7urmyj/u4tT3vgcNDeAPoqgaQnvwPHKNjY0EAgG2b9/O6tWrcTqdFBcXs379er761a/S29vL22+/nY69uMNut/9esh9tNtuYPxkjjRGRTdq50eDxeCgqKsLr9U7WstlJg8/n48iRI+zdu5fOzs4xbWOejEgCBYGiq2YS0DRsGrgiGjl9/bTv/5Az3/4e8uJFCAZB00AzvWqzJ/o/Gvr7+3G73Qk1D6fTyeuvv05hYSH79u1L2ZaU8vd6enoyljGQEdIYpWJ/hwkU8jMXhZ0/fz4TXcoIRpMOmqbR09PDvn372L9/v+UFhLHZMEKI0Y8319kAdk3DHVLJ8w3QdvwY+771bfwnT8GAHyJhUIcDqg9CPMjlco36+/PPP09vb++oxT8AhBCOwsLC3+np6clIQbkJ2zRGMfLfZpQAZldXF2+88YZVmnXJkiXMnz+f/Px8K4BVXV3NkSNHuHDhQkbri00EiQazaauYO42dOXNmxCrRsQzYlKTRD8KUIAINpwq5wSH6z53nyICfjb/po3DLFijMRyh2XW0UUfGb+LlxhnrVojF//nx6enpGrdFmFo5/4403Uu2VA7CmsLDwtzVNq1MUZUJF1zPhCPg3wJeT/WjuymW6ChsbG2OqM7rdbiorK1m8eLG1l2T8JkUzCVJKWlpa2LdvH1evXk25rDoVUkulWA+ckPp3zjAURDR8Ddf56I032DQ4SOnzz0HRPITdDjEu6NkHs0Z1KudQcXExa9eu5dSpU+lsj/hlIcTHwHfML8bjqJkoadagb3eRFE6n0xpYX/nKV6zdj1tbW7l37x5tbW3U19dbi8bM/U9mCqJtFFVVaWpq4v3336ehoQHdo6wwETtCUZQU6tlwP0ybRQgbUoJDSnLDYYJNtzj9gzd4PODH88ILUFqOcCqA1KWUZvwrYttMV+JMl2DasGEDR44cSelR3bhxI9/+9rfTXaL+r6WUp4QQ47YDJkqaL5PGdhdmxcaGhgaqq6utInkmogNYU1n5JR2Y0f5IJML169f54IMPaGxsNGIwEzMJhRDk5eWl1N0TnguAhkuTOIIRBrxeTr75Jqt6+ljxz/85lJaCQ4DdDmJ2Sp3Vq1dz6dIlLl68OGpcxiy82NjYmE78Zi36uP398fZrIm/9FUZRy6Lx2GOP4Xa7OXLkSMLfnU4nlZWVrF+/ftqCm6PZIea25//4j//IrVu3otbBmN4qIxs5DQghEFJXs5xOJw8//DC5ublp9lK/jtAEigRFgpAqDiTZkQg57fepf+cd6n/4Q2i6BaHwiKxnGd3vZNnTcdnS6SZZTwZ27tzJ4cOHU26i63K5aG5uTrfZL09kK8Pxkma+lPJfou9slRJOp5ONGzfS09OTzjZz04JkLmG/38/p06etmsXjjfJHtwn6S964cWPCYhKxJyR+RSJaJdSk7hwIh8nu7eX6nj18/IMfwM2bEBwCNQxSNQ7VkHL2iJ3i4mK2b9/OW2+9lZI4Y7Avs4F/aWxuPGaMlzS/MdY9LqOlzUxdQBYNKSU+n4/jx4+PWAdj/m5KjNHiI7HH6cjNz+OpLU/zwgsvWEu4E50X+3qibR9tWBBIxcp2ViS4Iyq5vV0079/Pqe98Dy5e0mM5asSyz6QYzpBOiOkULQlQXV3Nxo0beeuttxKu7A2FQjQ2No61jvdO9N3Axwzbn/3Zn431nFrgv6DvF5/+hWw2hBBcu3YNt9tNRUXFWK87ZZBS0tfXx4cffsjBgwfp6+uL+V1ExU5STdpCiGH7WxEUFRXx2c9+lm3btpGfn5+03pn1t6bBwAAtx08QbGrEFY7EjOf4YKoiJXZNxaaqdLbd497dVhaWl6HMK0EoCkLY9PMEw1kHswAVFRUIIdi1axeBQIDCwkKys7Pxer3s3r0bn8/HZz/72bHWYlsAHAXGtFW7faxBMCHEa4yz3vL69eu5cOECp06d4rHHHpv25MxE0XspJZ2dnRw8eJCTJ0/i9/tHpP4PJ1RGnSdipUlMm8Y6mHnz5vHcc8+xceNGXC4XiqIYfTCNiPjZP7VXLpp01opQwKlq5Pv99Jw9y5FgkKd+4zdxPr0J8vJ04sxCl/T69euZP38+u3fvttZXmUi1E3YSrAFek1L+p7GcZPvP//k/j+X4jUKI/wIUJvoxFApx7949fD4foVCI7OyRJk95eTnnzp0jFAqxbNmysVw744jPDZNS0tbWxu7du/n4448JBAIxgzHZLmwmeZKNQSEEFRUV7Ny5kw0bNliEGW4zmVNX6u5iQ9IM3W7CFQ6nHutGdrRNk9g1ib+rm+bGRgrz8sguKwOH01heLWYdcYqKitiwYQMlJSVWeOKzn/2staJ3HFgEnAZa0z3BPkaD9lVgabIfzS3wEsHMBigtLaWoqIj29pmzV4+UEk3TuH37Nnv37uXq1avW4qREEmaEKpVMwkiJzWZjyZIlvPDCC6xcuTJJJU1T4ozSx7i+xF5fJ6A0VmwKqViL1ByaRm5wiMFPrnPm+2+wYXCA4u3Pw4L5hmCLlm4zd8VnPNLNhk8jUL5UCPEqcCrda48lTvMUOmkSwiwGlww9PT309PRY2QC/9mu/NoZLZw4jpIQRg7l58yYffPABn3zyieUhGw+iJZPdbqeqqorPf/7zVFVVYbfbrWuax8afl+nKmjYktkgYgYb/9h2O/ODHrPQN8PA/exkWlILdBopt1kmcVDArr/b09PC7v/u7qWydV4F3gWPptD0W0nyRUYz/vLw8vv710VeXmtkAY9nxeDIQPWOHQiEuXbrE3r17uXv37oQIE9220+lk1apVPPfccyxdulS3XwRIZJS72BZ1HlZlTRKt9IyKB2mGRFHMck8its/m3SlS97MpQmCTGu5ICO1eO/X/8H8RPh/LX30FFlaA3QF2+7B3LQl5Z0HKmlXIxJycN23alNI5IKUsF0J8sbe3N6OkWQ98YbSOjrZnjAmn0zmjdhQLBAJcunSJDz74gPb2djRNy8hM73Q6Wbt2Lc8//zzl5eXWQNR5IaKkkZnSj0EYw5C3Bu6Eu2IRS0hwqBq5Mkzw/n0uvfce/YFB1v2LfwFLKwGBsCf25M0GRG8uDPqK4MLCQi5cuMDKlStHnaSN+/0C8FPgTKprpUuaXweS7pZ08OBBXC7XjKkOkw4GBwc5deoUhw4dGnUdzGiwyGCOewk5OTk88cQTfPazn6WkZAGmnaAo6EFFCUihe8xUw2um2PR/ozwKsQ4CGM5x0ywixMPkn/m7Job/X5GA0FA0iQugu4fWf/oAW/8Aj/3mb0LNChBZSEVJStbhr2eW7XPx4kVroymPx8NnPvMZa3JesGABb731VjqF1RcWFhb+eiQSyQhpqoGX4780B5nP57NE4apVq2ZUsmUyhMNhTp48yb59+/D5fONeexJ/Xl5eHlu2bOGZZ56J2vtTH+z6sUKPzKuAGoGeHro77pO/eAn2vHxjDE7uLG9Hd3pkS3D09nPn4EH6/X62/L//ClauQmS7DC3QlrKtmQCzBFhRUVHCwumrV69maGiIPXv28Oqrr6YKc7wM/BgYNW0lneDml4F/Hv1F9KyclZWFpmm0trbS2NjImjVrsNlm9gMfHBxkz549MdHliRjhihAUFepBy61bt5KXlxfnbNCMSLxA0VQIheD2bep+8jaXjx6hatVK7EX5evzEioQKRIzL+RjBpiY9uCk0w2UXb/fEfjMiCIoCCBRDiimaxKapDHrbudfUyILiYhylC8CRlYLAMydlwO12W/WfS0tLEx5TUVFBc3MzAwMDqYLqJYqitCiKclxRFJJ+UvSpEBihc8UH+7Zs2YLH4yEQCKS1/HS6YPZZ07QR6/jHSxghBGVlZXzuc5/j6aefJicnJ2l0X1EjEAzDJ59w5AdvcHX3L1Ha28kKRwxNTBu31BsNmgCpxHoMFalLHVckQr7fT++Fi3z0vf9D78HD0NcHERUm6BSZCng8nrQSfZ9//nlOnTqVznL6F0kShzSRijQ70V3NIxA/MHbu3GmVYmpqakrVsWnBSGIoCZMXo+MuidJkhiPvNhZVLObFF1/Uo/zZWbGeLE2XFFJKfRAGgnDhEie/9R3uffgR2f39uCIqQmpmBy1HwcjLRr0qqSRN5EwEMyM6+W8a2aEIkWvXOPnd73Hvl7vhfpdBHImUKhoqmuWqSBYzmrk1CsykYXMrllHwFPq4T4p0SJMWiouLraqXZi7QTMZEPERCCOx2O0uXLuXll19m9erVMUFLq/i5ECA1RESDAR9Dp8+w/zvf4t6JE+T7fOSEQzikXj1TP3Z6dkBTJGSpEQqCIbTbtzn+wx/R/O4voK0dwiFMbTA+iDvb6hDU1NRw7dq1dBKGx02ax4ExlWNavXo1tbW1BAIBaxfmmQZTtVRVfYa3ZvkoREuXRNF+m81GbW0tX3zlC1TXrrCClqCgoUSdr+oVMvv76Tt0hKN/+zcE6s6S4x/EpUUQM2jrJkvihENke71c+MmbNPzdj6HpDoTCCE13Yoi45dexxEl/XdF0IC8vz1pCnQI70Md/QijmrBj/AbYDY45APv/889YqujNnUnrvpgWRSGREQfJkiJ/5nU4na9as4eWXX6ayshK73R6V9ayb2mgSoaoQikBPLx1793PkjTcIXGugwD+EW1VRNJlUZZouKBKyIho5oRBZnZ1cfe+XnHvjDfjkFgT8CFUzorCxjpPZJHHKyspoaWlJdVgx+vhPiGTTgmO0k0aDWeDa7XZz6NChlAuHJhPJXqaZaybF6Kn98cmaOTk5bNq0iRdffBGPx4PNZiPmEubaGTS9aHlHJ83v/IJTb/wfxCc3yAmr2CQoxhoYbUya2NTZCjZsZKuQ29dL894DnP7W/4a68xAMGWaLQRxSL42YaQgGgzHltkbBdpKUJEuWsPmslHJruvq1z+fD5/PR399v7Q9fWVlJU1PTtKbLTNQ+iCZdTk4OTz31FM8++yz5+flR14i6liZ1j1NYhfZ2brzzDhff24X7fjt5oXCUZImfq5Qk3wNiag1r/T7ApqrkaGCTPtqOH2d/fy9bfvd3cK1bB84sPWcNXbrOlgSCrq4u6uvrqaqqSufwrcCzwAh3cLLg5rbRBtyZM2fo6+ujt7d3VP1w27Zt6XRuRkNfB1PIli3P8ORTm8nNyTfWv8TlYmmGpRwegtut1P/0LW7s20d2d5eujhkeLLPS/0zS/aMzCcz1PcL426lCnn+A/kuXOPHjv+PZvHyorga7zcijm/mM8Xq9XL161UooThbPSYBtpEkaF/BMslaampo4dOhQzHdmnk9BQQH5+fnWTDyT8szGmyYzf/58nn/+OdatewJ3drbudB32u+ptSkCL6OpL403O/uDvaD5ymNz+PrIjEYTp8hXaiEIXMx12TUOR+vKCznN1XN+7jxULFyIcdrDZkGJmEicUCnHt2jVOnz5t7TpQVFREZWVlWvloBp5B50NM8YFEpNkCbEjWypUrV/B4POzcuXNaVa+xYjyqWnl5OTt27BheZSplVMDesHdMlSwQgksXOfuTn9B27AT5gwNkqRGE1FDMmIpUZuDwGhnDiee1IhXsqkrOoJ/GEydYseMFKMhHKPpCNt1jnniB3nTg4sWLMd7btWvXsnLlSmsSz8rK4t133+W3fuu3UgVFN6DzYX/0l4lI89RoN15QUEB9fT11dXXpVDSclTAXju3YsYPa2tqoGIyZBa3odfikBqoK/gChugscf+MN+i5cJDfgxxWJoMzQQN94YJcCt6bR336P9qYblFU/BA49LSd+XdB0IysrC4/HwxNPPEFVVdUIYmzZsoW+vj5OnTqVTgnkp0iDNJtHu/EtW7Zw584d6urqKCgoYP36jNSUnjGw2WzU1NSwc+dOli5dahUE0RE1m0pNj8EM+gh+dIKjf/f3+BvqyR0awqkmJovZyozV0EynQ4JsA4GGXQUZCjLY1aNPFiPT32YE0tkx7/nnn+e73/0ua9asSbWkZfPAwEDMF/FPZw3wZKpOvfrqqxQVFVlVMx8UOJ1Oamtreemll6isrIwjjDmgJELTIKxnKXfsP8DB732PQP1V8oZCMUb/gwaBhohEGOzrNUgjkchZFacxMYa0mieJKyQTT5oN6IZPygt+4QtfwO12s2vXrgeCOC6Xi02bNvHFL36RRYsWWZnawzWUDWjoMZiuLrw//wUnv/1d5K2b5IWCONSwkQxpqHFGjpgmjKRJkkiZpD9MMUbJaVMMdVQAajgCquHU0GRMPGs2wUyrSQFXbm7uhtzcXMxP/BNKW9cqLi7mtddem/XEMYOWmzdv5oUXXqCsrCxuEGj6Emg1AhFDwrS1c/Nn73D2J29hb72rR9DVKC8ZwCwcRImQSFW3CzvGSrkJZ4lPJ8w62mkE4NdHIhHMTzRpsoAnxnJRs2QowK5du2ZsdnMyCCEoLCzkueeeY8eOHRQVFenfR629tyLeZqZy8x2u/eCH1L/1Dzju3yM7EsauaSCV4aXFIjarbDarayNzyzB2kp69ZIlGUVERra0pqzc9gc4PIFY9WwOsGquYra6u5vXXX8ftds+K7OZolJSUsGPHDrZu3Up+fn5M2VYTAg1F02AoAp80cPqHP6Dh/X/C3dVJbjhsxDFiB89Ei3PMVJjPR9hn9iLDsSKNPMRVdrt9jd1ux263jyDNuGaOrKwstm7dSiAQGPO5k4oEtoL54j0eD5/73OfYtGkT2dnZCCGsAn46VXQDV6joRcSvXOb0d7+D94MPyO3rwxUxDX7FatfEbJ99o5HInSyEAEXMikVqoyEUCtHW1pbu4ZYzINrl/Gi6Z3d1ddHa2sonn3wSk0bj8XjGWkt3WpCbm8vnPvc5ysrKotL6hyEEKCq6lywwSOTCJY7+6Mf0n/2YPP8gWaqGbZaqW2OFtdrVLPYhhO4kmcUTg7kf0rFjxwgEAmRlZaU+KYofaZOmq6uLq1ev0tDQYKUlmCgqKqK6utra2nzGIC4wYq47NAuwj5QIw1VWhBqBAR/BM2c5+YMf0X/lCjlDAbJUDSFjK718WmCtWDUl8iwhjkmSlpYW7ty5E1MbwuPxpFvSdgRpFgKrkh29e/dua3s/E9FEmS3pNIkqWprQ/98IWkZU6O2n98hhTrz5JpGGTygYCur2CxraDEq2nEpYFaZss+f+zU2So+HxeCgrK2PRokVpl7dF58dCoNUkTQ2Qn+xo0yvmdrt57LHHZjxRYrKPISq9Pu5lGwX6BPpSY6SEkE6Yzr37+fitnyDv3CYnHMGumZIldTWSBx2zSdLk5eVRVVVFaWkpixYtoqKiYry7VeSj88QizYpkR/p8PsrLy3nkkUfGwsoZg1SVKk3CCFXVo9z3O2jbs5dTP/0HnN675IXDs9pl/GmH0+nkS1/6UqaaWwEcMEmzPNlReXl5mbxoZjFCpMT9aRaDEIn3fRFmtX5N6kHL9jY+efvnXN+9G+e9+2SreuFw00OmrzvR/54J2bzTik/nrS+HYZvmoWnsyORi1P0rVN1DFopAaytXfvIPNOz5J3K7u8mORK+0jCo4Hr1S81MIa4n2p/P+H4Jh0kzv7krjRar3NuL3qCxec0HYUAhu3uTST37KrQO/Ire3H5dUYwkjGM7JMs+FpHlac3hgsQx00pQDldPblymGMDxkwRDatat8/Hd/T8vRY+T7Bo0sZZ0UWvT6fKENb540HX2ew0xAJVBuR98+bYQ74cHQ2fVBb1bR1DPCDAkTHIK68xz9wQ/oq6sjf2AQl2rYN0InjBInSSyyzEmYTyucwCIFSFgRevYTZhjWUlzzPxEN2XybI3//93SdqyN70E9ORC+v9CDd9xwmBRUKY9zafLZCr5FsM6RMgBtHj9J2vo7cYMBYaRkrPaKljLkeJurXEcfP4VODcgUom+5eTCqiKkJK0Ath9PpoPn8R58AArnA4LgYzu5MQ5zC5GBgYKLMDC6a7I+NHXKQ/Pm4TZ3sI85jeXga993CqYI+29eMIYzY3MrD5YBArfue0OaSGy+VaoAAl5hezb8lqGmqSGL4vKY2C5IEAMjgUl6msxf07QzDndJgxMLa4L7ED88wvZ70RHN/9qECkhtRdxkJAKKwvX05IlGkYpILRY05SAkrG03nG3JY0Jilp1j+YbZPsxGDwY55Cil2fHgRoDBcxRwgIh5BaRP9tNswTM2gyi1/Z+iAjkeYlpSxUGCW7ebYifsGmghjeZ0ZCJBhAC+ukiZ5tZdxnZkFL4MXLUMuT1O5sRyLNSwiRrwA5U9+dyUWi9289ACmRqmZsUDQHGJuaNvvs3owjRwGyp7sXGUPU+4wmxPBekfqekFINg7Gp0uwhTuZtreh7nyPO6Ii652yFqNI0DwQSvM/4qvZaRNWL3c0YpOe5m4zBOtZJwxZ9xiwtEjgeRKlqWQpJdnt6MKFYn5nwsm3D7r3YHzR9Ow9priaF2F2jMwTTdkvlvIuHYouRURnt0yyA41OTDyJnoGmvkyLJ45+BeqPZJb0azcjUo08JFIU4nWAmzMDjRpIpU0DMLs6KYkdR9KVE03u31jBkOKtBNQwMY1AKjPoFUQNUaBndVjDeW5jKm5Zox+sRbc7mcTQ6NAUIR38z6wOcCaHF3JdisyEVMSPcrEKiF92Tms6TqBEszGUM5v/PAFhFFRMUSYw/7gFF2A4M8aA5AyzEracRml602z5c7G4665epmBLDtF3MSvyGVFE00MIg9VprQjMLuE2uWjRiZzR9q7PhL0S6tZyTVAGa3RiyA34ewABnYuipHzabDcVum1bVTKBhx449OATtXnA5QLHpH7Pcq5AQCUNnNy6/Xy+0bp4/hZ6r6ILu1uLEB1aQpITfDgxOdy8mC9Ka4cwojdSrljnsUXWbpwdCglONEGpu5vhf/k9Udw4oAk2KmOxjRarYQ2H87e1kaSqmrTPVNoNZCksIMVzcx8iJG8aD6RyIXsUspRy0A/3T26XMIuUybSkhZoez6YGuAmng9xO4cQup2Ky+a1bf9ABsWIJNU7FHTQNTDRsCDT2TQgtF9BpxZi9jBpWqL/Z7QBA/noQQ/XagN9kBswnRO5ZJqegzIprxvWLsTGYc4/cjwuEZoGHomctZSH3DKMCy/BXDZjCyFhTD7BEGbYQQaJMsbSzqRnnqZDhE3717VATD4DKK88Q9yNk8juIRfR/GffXage5EB8wGRL8c/d9hPV9KVd8jUpNG9RkQUsKAj67rDWg+H7YZ4ha1aRDt+RfonmdrP5io701Ml0vXpqrcu3qFWq8Xst0owm44BvQO6q9hdo2j0TByjNGtAJ2ZegFTnR0cT3LdS6YYrlqJuSckQ2EYHICWZkK/+ojrBz4kZ2AApzr2bcszOVhtQurXF5pu/wsNTWjDRrfhFo/PvrY+ydy9JE9aHcvEaL1PYy9OBcgKRxi8cplbb/091NXB/S7wB/QKpVLqi/ykiqZF0DRtbM/LfF/xX2fsmRu5h6Q/TqNsGQAGBgY67cD92SZhTOizgDTiG6rhvZWgqgg1DL4B1PZ79LQ009N0h7arV+muryfr3j1yI3rSZro6miZ0l69UorcGVGIevrnORFWwdhaIX3sSH0SE4UVyZrFcmyExky1HFnHfKVGrU6P3/dRL6po91dsUZvFD89yxjEep4NQ0pK+fqx98QEP9NebXrqRs+XLmLVlMbkU5LFgAOdm6o0URYLMZu9wayScpCzwOD9KZtJ+n2QeXy3XfDrRnrOEJnh///ka2F1vZUieMptsDoSEYGITOLmhvo/PmTVquXqH9ZiOhrm7wB7CHhnCrkeFNZREjRzWgGO5c0+zWhKZ73hR9AZbUhO7pQkEK0AyXrBSgChhSBKrNhqYIbA4nUtgQNsVy3QohUBwOpE0B4zdselsCG5ERz0VFi6jYhL6sQaoaMqzP5HqmtkSq+t9EwqDq39tVFYeU2CQoUqJIYRHOjOoPx6lMN7e+f6gQAtV4I2ZlHiHBpmm4hMTu9+O/3oD31g3uObMgOwd3cTFlKx5m4apHKKyqBE8ZFBVBbh44HIZjLYErw7SZLLecZnBnjCPKHEBJT1NG/zkN2O32diGl/GfAL6z9JqfBiLOuzXCOmDByk63+SFWPmquaMTpVvaTs4ADq3VaaL1/l/o3r9N5pYaj9HuHuLhyBIbLUCE5VYpMaitT3l5FCIKWwZniEzRogEsVQiQSqUHSpIXTpIYT+nWJzgN2BtCvYHU5sWS6E3UaWy0XB/PlkFRaguJwIh4OcgnzcObk4XdlGGMaGothxZrkRTjvC7gSDNPoWbDZT9xquBGJITzQVGVFRh4Ko4TBqWENKnVBqeIigf5CB/n6GAkEIhQkPDDDQ3Yu/txcZDqGFwoSGgvqGVWEVzVgiYZOgqLoNqCARUhgk06WrTSgomrn+SNPJK3XJKIUubVVFIaTYGLI7COfkYJ83j7yFHooql1K6ohrPqlUwfz5ku8Cepd+vwrA9pOiqtf4SVEPiKMb+pQqKko5n1Ph3cofvrwsp5XrgdGbaG1sEOJosxhfWbzqBdbemgGGJ4g/o0uT2bdqvXKHt2nXabt4g1NWNCAWxhcLYkdilxK4KbIZKIjTVeuA6KfSZXVUgjCBiU5CKjbDdhmZzomU5ICsLR0EhroJ87Dk55BcXYc/NxV1QRO68IuzubHKLCsgtLIJsN9gd4MqCrCywGyQQAhS75Q0zB8OwdW9+bz4DxUg9FoCMzTEzyWQp5cNuXz0zWoJqZEVFjG3cA0MQDsFQkIhvgL6ODsKBAP7+Pga6uwkNDDDU6yPQ20t4wMdgdzdyaAgxFMIejmCLRLCpYRyaxC41hDHx6JLaNux4MZ6pKhQiiiCsgFQchG02cLvILZuPp7aGstpa5q2ogSVLoCAPnOazMtQ3RRhu6ygSEUuY8U/sGclQ2CCklOXAbRKUph07xk4aGNaQTJVBoIFq/KFGIByG3j4CN25y5+JF2i5fpbexEdHZjTMYxBkJY9cMaWLMgtJYAqAaxJCKIAKG9FBQbQLsDmzZ2dhz8yiYPx/3vAIKF5SSVVBA/vxSsgoLcM4rgqJiyM6CLBc4HWB36i/aZjPIIIatb8FwmomUsS/bvG8xrO4kfPmpZkzr92F1VWq6Q8FqT9X0fFypmiPaMNSlIbGN56qqeoneoSD0+9A6Ohny+ei7d59gbzeDXd34Ozvo7+zE39tDJOBHDYawqSpOKXRJJDVLMilo2LRhG0pXXyGoCIYcdrT8PJT5pZSseJhFjzxKeW01jkVLoKgAbA79eRoSSI7YImWi9s34JvUohIClwhi4DYyysdNkIVodAzMmYSQvhjXo98FdL76rl7l+/ARtV6+i9vSiGFUxHdYLi01ojCDQFBtDSFSng4jLhXS7cBQUYM8vwFU0j9zyUoorFlJauYSc4vlQUKCrDi6XLjFsCiCMl2gE68xdjU2WK7Zh/zBYg1wycv+a2LQXU4e3RZ82+rNKMbtGx6miryfMfke73UAnnLFuR1cBzd803TBTI7onbCgIQT/4fET6fNy700y39y49LXcZ6upkqKcH1dePOuBHBII4wiFcqmptFQ8aAtCkQNoVwgjCikC1O9HcWTjmlVC2spaHNm4kv+ZhqKiA3AJw2JGKgrQJhBS6tiqmVcIAXAeqTdL8E7Bzoi2OG5phr0Q0XZUYGCBw/RNuHD9J+8VLDDQ1Ivr6yI6EcWqaEdfAsjkiQqAqoNpsYHegOrJw5OaQs6CE0sqllCxaSp6nDPeCUn1Gyy+AnBxw54DDUJ1sImr0RqtSUd00vo+WEKoGiiEihTBjRHHnjxjwRhB2HIbp6O5XLUaFMWdqZZjNuvQz/xS67WjZkaakj7ZtpaqrxqZnUlP1ElgDAzDgg+4eZE8v/e336LrbQkfjHXqabxP2DaAGAhAK6RNbJILTtJGkhqIoaFIQsgn8jiy0/FxcixYyv6aa5Rs2UbRyFcwrAJd7eHKyJPtYkTHS7AE+Z5Lmb4A/iP51MhwCsW1qxi5kGEb9ELR58V+6QtPp09w5d47g/Q6cQ0M4NA27psc0pJRIm50wCmGHnVCWA/LyyS4tJa+sjMJFiyhespiSRUtwlC2AgnxDb7brxIiWGiLatoj6VyrGwIra7VkI3VulKKDpA2+03aH1+x1dN8/E8xwbYvtn2Xhx0j7+GvqkIIbjKKZklWZWNrotFVb1eFhXN913btNzt42eu630trbia2tD6+7G5vfjDEVwqKql0mlCEFEgrCiEHU7s+fksqK5h8RPrKF+zGqqWQV4+ZNnBHrX2iLjnOUKtzXiW9TeBPzRJ82+Bv81Uy1bMIKG+rumzlWZ4hIJBaG7lztmzNJ44QfcnDdi6enCGQlYFTAlEFIWITaA5s3DmF1DoqWDe4oWUVFVStGgpjkULoaREV7GyssFhM2YmjClVJJ/S420E6yEPLy2InX1J0lbi82dWEuPIpErL1kKOJI1IZHvFle+VRoksReq2qGbYTBEVAkHo74e7d+lvvkPPnWa6brfQ2XwHf2cXMuDHrqrYVBWbuS+QlITtDoays3EtXEjF2sdYtmE9BY88CsVF4DA8jopRuFCJIo2IJtLI5z/BSev3gf9tkuY5YP94WzKhytjFXuZqSSmUYXslourR495+uHUL75mPuXb0CP137mDz+7GrYeyqRFMEIbuDiNuNrbAAZ+kC8hZ6KH/4YSpW1OCqWAj5ebrXymY3CGKmcyjpkSUDSMyf0cky+Z7RySVrIvkmYn40JJA07SbD+RCJGOq3Hzq7uH/zJm03btDdeAt/+z1CnR1Inw9naAhbRCKEjYiioDoc2OYVUr76UVY8/RS5qx8FTwW4XWC362qzIlAwHS82470kfg5jIU7csduBAyZpFgJXmcC6GnOhUrRhr0ht+MFp6AHI/j6C9Q1cPnSE9ouX8Hu92P0B7EbFS2lXsLlycMwroLymmoWrVlFc9RCUlULxPJ0oDqc+uwjFUrei1aWpcdePdp0HmzQwOnGSu4eN9CbVdI9HwO+H3j7ouM9Q613aP7lO84WL9N31ovb7kaEhFC2MKhRCdgeiqICCh5az/MlNVD21WQ+gZrmGg6dCGbYVLbGZkefQD6wEWkWUXnwceHLkjY6G2JWRJqRhXgpTDQv4oaMT//k6bp06yZ2P6wh2diEjgCJQs7KwFxVQuHQx85ctp3z5Qyx4qAo8HsjJBkeWLkUUBSvtl5EuyeR91qwZaGww1q6Y7Y/x7HhknCxjbXCiHYgTq9HNxY8ZfRI1A8bCClZbJ0rVCCtESSLTtu3tJXinhbYbN7h38xO6bjYycNeLHBxEiRgxomwXJSuW89DmTVRs3AjLlkGOW7ddDeJYMcDM2JEngM0A0aT5DvDV6JtOfaG4GU0zvCsS4wEE4d59Ws59zLXjx2m/chnZ14szLHE4HOQUl1C+fDnlK1cy/+GHUKqqoLAA3G4jHjIc9DJ9/vp1dANbCiXG+6MkHQ0Tn3mTmjFjbIMMtDPuBjNBmqjzUzWnoRpUMSP9sbZutCPDskHMsaOaqVF+uNfG4I0btNc34G24Tk9zKwFfPyEhibjd5Fcupebpp6l96ilYsgicbn3sKHrAOkOOl+8CvwOxpPkd4NtjaSXmoUl0wkQiujuyuYWOjz/m0pGjtN26RSjox5aTQ+6C+RQvrmLpI6tYvGoVSoUH8nJ116/N2GzaJozgFiQb6NGZBPGPZLxjY8R5E2TKcAqmMrL9TLDQwgQnhUnSF6NTs2CkuhTv/RNGYNp6Pkg9DKFJ3Ts3NATdvfiammi6fFkn0N1W/N09SCnxLF3C6m3PUrFpEyxeCNmGQygzi+J+F12wxJBmI3Ay7SYkurdJano2RyQMg36Gmlu49NEhbp07T3/zHewRlaL8ApY/9ihVa9eQXVUFpeWQn6+nnNhsYDOyiK2CEcPBPz22H6f+RWUSxIj9qK5Bhkgznoas00eSJlOEjMXMJE3MJaKCr0LYRhBK/17/f818q1EZFcJyMESlU3V3Qctd7jc0cO1cHXcamwgKyKtcyuptW6l9+mndo+pwGKp9GlnWybEJOAWxpMmS8LGAVXrPZcJAkqYZlVGkBkgID0F3P8Hr1/jkzBmu1NXR3dlDYXEJ5Q8/ROXKlSypqca2cKGRn2W4C62FSxO6kRmPqU6Anej1ZvSqS3Oi1ohyMBne2O4eem810XzjOreuXaO9vZ1FSxaz9qmn8axdC0WF4HQaCfKxcao0cKWvr+9x9MpNMaRBwg8F/KvhDibquGHARcLQ00PLlatcOXaUlmvXsEdUllVW8vCq1ZTX1ugiMi8PnE5doghlnBHdBxPTPUCn+/oZgelU0IxwxtAQ9PUz0HSLWw0NNLa24iyZz+otT1Px8MOIbDeaEXcbw73/qK+v78vmHyJOr/wqht6WFFoYQmG8Vy9zZtf7dDa3kFtYRFV1NSvWPEbBkkWQk6+LRDMAJRj2dImR6lSmkGwQPBCDg9FcuZ9e6DaQQMqIsQjRzLGKgKoR7u6h5U4zd+/dx1O1hCXLH8KWnQ0ifQdBJBL5HXRHAAD2uN9PA0HAFdOp6Mal7iL09Q9S5qngM1ufIW9ppR5DcbvBadNVT8UWswPZ5FFlGMkewmQOrrEO3okM9vgE0DlEe+IUpF0ANt10sNsQEhzlbqoWLKAqOIQ/FIjK7Ej7EsFgMBizdCZe0gD8Csmz+q+xP+gODVVf9hsM6saZ3T4ciVeMtBUxTBgYzRX8YGFu9k8PmX9Ow3G46BQuTdOMsWcuoZDWuh89gyCtPhwcGBj4TPQX8ZIG9CDns8nbUNCEhCy3ngEQd11FxK6bn0wkevjppNCbiZeZeHGqqlptTbXqlOw6mlGJc7oLIiaDlShqJsBOEEkTVxU9kUZgi82rG8OrkVIez8mJ3SzQnuByx5K+b9P9JwQYy0+t9fTCTDM3jmMUFSJDLs74CHRfXx9er5eqqipcLlfCc7q7uzl37hyPP/448+bNm9D1g8EgH3/8MRUVFSxevJi2tjYURaG0tDRmMJgqweDgIMFgMGaw22w28vLyUBQFv99vVXBJ9OycTmfMfcUfo2ka4XCYK1eukJ+fz7JlyxIOytFInc5AHjUhNwXMaw8ODlJfX8/ChQspKyuzfh9PmwIbw8vDh9sYXuoARCWdmhkM6Yw/IcSx+O8SSZoj6LbNhgQNJP47zrhPa/Y3ctUyiaGhIY4ePYqUkpqamoQeksHBQa5cuUJNTc2ESROJRLhy5QqaplFRUYHX6+Xy5cs8+eSTPPTQQ9iMSp5SSlRV5ejRo1y9ehWHQ99HS9M0cnNzefnll8nJyWHv3r20tbVht9tHzJ6RSIS1a9eyefPmpM4OTdO4du0aR48eZfv27UQiEX0vmQTHKoqCogwvlpNGTKS3t5eenp40FrzpkkzTNOt8RVGYN28eBQUFo54biUS4fPky586do6SkJEbyj1tKJzh3NBswzWucRudDDBKRJgh8BGyQ1rIrc926eUjcuowUVx7RQV0MpdHn9CGEoKioiIqKCurr6y1pkylVKZHnSlEU7HY7qqpis9l45JFHUFWV06dPEwqFWLlypTUYNE2jq6sLl8vFunXrsNls9PT0cOnSJYaGhsjKyqKtrY2CggKWL19uEV7TNCKRCOfOnaOzsxNN0ywiRA94TdO4ffs2v/rVr/D5fDQ2NuL1ehPev6ZplJaWUltbaxHYvNb169c5e/aspeJF338wGMTn81FUVGSdF90Pu93O5s2bWbNmzajP8fbt25w7d47HHnuMhQsXWpLN7/cTCoUoKChISdqY3825e8RIHB6n44wlf4TOhxjYkzR0CPj62K+RHjK9ECscDlsvuba2llu3bhEIBKwB63TGlj+Ij0TH98lUc1L12ZzJg8EgoVAIIQSrVq3C6XRis9ksMllqgRB4PB7WrVuHw+Ggra2NhoYGiyA2m41ly5axYcOGmH6GQiFaWloSzpamFGtsbGTfvn3k5ORQVVWFpmn4/X7rOPMeent7+eSTT3jqqaeora0d0d4jjzxCVVVVQlvt0qVLHDt2jBdeeIEFCxZYz1zTNDRNw+FwkJeXN6rrv6uri2PHjrF06VIee+wxHA6HRfpPPvmEhoYGtmzZQllZWVI1cQqdLYcSfZlI0gAcBA4LlK3WNwJSpmtE0Xk0Zo/1pkd7CUNDQxw/fhyv1wsMv8C2tjaEEMyfP5+nn36aaGMuFApx69YtfD5fzIBesGAB8+bNo6mpiRMnTowgTnx/zNm9tbWVtrY263dTZent7WXjxo3Y7XbrOvED0WzP/DfaQZHqOUkp8fv9XL16lXPnzhEMBlm8eDHPPPMM2dnZMW0D3Lt3j9OnT7NmzRrWr18fIy3MvuXm5mK327lw4QKlpaUsWbLEGrz5+fk4HA6Ki4tZsGCBpdI1NDTQ3t7O+vXrcbvdCdVzgP7+fk6cOEFOTg6bNm2y3on5PJYuXUpjYyMffvghzz77LB6PZ6xByDhE2ZVjP/kwOg9GIBlpwuiL0rYm+T0lMjkXjBZ/URSF4uJi68VGSwspJQUFBSNmrEAgwIkTJ2KMarvdztNPP01RURFZWVnMmzcPVVVHfWGaptHd3Y2qqjF9MAdJbm6udaz5nd/vp7e3F7vdTn9/v0Ww+PuUUuL1emlpaSEcDtPd3U1xcXHM9QcGBjh8+DDNzc08/vjjlJSUcPz4cU6ePMkTTzxBQUEBNpuNQCDA7du3uXjxIsXFxaxfv35Uu8Nms9HR0YHX68Xj8ZCVlXzPr2AwyOXLl7Hb7djtyYaT3tcTJ04QDAbZtm0bhYWFMb8LIcjPz2fbtm0cOHCAAwcOsGPHjhgnwRRjP3G7BJpI5D2zThLw74GoN5V4maz1qpM5y6J070yLVofDQW1tLe3t7SxYsMB6wV6vl/v371NTU2PZNiays7PZvn07ZWVlMf3Jzc1FCEFFRYU1y0Fy0mqahtPppLe3l507dybU86Ova6og3d3dCCEIBoMEAoGYNs3ZWwiBqqp0d3ejaRqPPPII1dXVI7xyCxYsYNWqVVRUVGCz2XC73Zw7d449e/ZQVVWF2+3m1q1bRCIRampqWLFiBW63O+H9RNsmK1eu5MCBA9y7d4/FixcnPb6trY3u7m6ef/75hOSSUuLz+Th58iQdHR08+eSTZGdnMzg4SCQSQVVVQqEQoVCIoaEhIpEIeXl5XLhwASEEL7744ojJIjXiNKL4QZ56CHYxykrm5FMDfAzsBV5LdoBI0B9gBJvSVTfSRbw6EwqFOHnyJOvXr2fp0qUA3Llzh6amJlasWDGCrA6Hg5KSkhGz2FjJLYTA7XbT3t5OJBKJIU2ie1YUhcrKSh5//HEURaG7u5szZ87E3E84HGZoaAiA4uJinnrqKUui2mw2i1SKopCTk8O6deusPkciEdxuN4sXL6a+vp6GhgYURSErK4tnn32WhQsXWmQ01Z7ofkbfe2lpKTk5OVy/fh2Px5PQCxcOh7l+/TqlpaWUl5dbKmv0PQ8NDXH48GGOHTtGUVERhw4dIhwOI4QgHA4TiUSs6yuKgtPpRNM0ysvL8Xq9nDhxgs985jMJ1b7kUIivYzBG7EUf/wmRzBFgYg/w2mjrTBKen6RRTdPo7e0lGNQdEuP18wO43W4KCgqQUlpqQUdHB0uXLrWuk5OTg8PhSGoPjeh21HE9PT0jDOn4YwsLC8nJySEQCBAOh5PO4Obxpo1letXa2to4f/68dYyqqpw4cYIbN25YjoFoFBcXs2XLFvLy8hgaGqK/v59AIIDP56Onp4euri56e3tRVZWqqioWL16Moii0tLRw7do1rl69Sk5ODvn5+VbfTY9jtBcNwOVysWTJEtra2hgaGrJspOjn19nZSUdHB5s3b7akebwqrCgKBQUFbNiwgaysLLKzs3E6nWRnZ5OVlYXdbsfpdOJwOCwHimnX3blzh6NHj9La2sry5ctjri2EGDE5x/4Z1Y+xD7M9o/04mqQxTz4GPDXil3H48EKhEEePHqWhoWFsJ8ZfWkpWrlzJ888/j8PhwG63k5ubS1dXF6qqoqoqPp+PkpISy4ulGjt3mZ62cDhMKBSyXoDNZrMGqaqq1NXVcfHiRet6fr/ferkAWVlZbN++nZycHILBIIODg+TnJy+xYJI00YwdPcO7XC5aW1tZsWIFHo/HOqa1tZXm5mbr+IGBAfbt20d3dzeKolBYWIjH42HFihWUlJSQlZVFKBQiLy+PVatWMTg4SFdXFx0dHbS3t3P9+nV8Ph+1tbWUl5eP6JOiKDz66KMsX77cUrviJ5q8vDyefvppKioqrN/jJxiHw8HGjRtjJFt0TCb6+cTbhNXV1eTm5o6Ip02y9+wYEyRNL7BbxJNmHH02JUJ1dTXz58+fsH1jEsJ8CYWFhbS1tRGJ6NXzA4EAJSUllnenvr7e0q87Ojo4dOiQZcOA7qqura21gn4rV66koqLCIszBgwdZtmyZFTS12WyUlZVZgUCfz2epe8kkm+k+7u/vRwg9Kh49cBRFYfXq1XR0dFBeXs6TTz5pBRAPHTqEy+Wy1JSCggJLdcvJySEnJweXy2Vd+/bt2xw5coQdO3awYMECXC4X8+bNY/ny5ZYdEQgEcDqdOJ1OVFWlubmZtra2mD4JIbh16xYAjY2NDAwMUFdXR2FhoXUtr9cb44ipqKhg4cKF1t8Oh4Oenh7a29tjgqHJXOjm/5eVlVFZWZl8nCQI/2UAu4naHTAR7KZOOQp+abfbv8wEy9aaBubDDz887vPNgRf9nflvQUEBTU1NRCIRyyYwydnc3MzNmzepqqoiPz+fNWvWWC85Eolw8+ZNsrOzqampsdorKyuzSODz+Th79iwej8ciDWCRwG6309nZyUMPPTQqYcLhMHV1dTQ3N1vnmn01z8vKymLFihXU19fj9/vJzc1lcHCQ9vZ2Vq5caUk6u93O4sWLGRwcZGhoCL/fj9/vt57PjRs36OrqYmBgYIR0M6+Vk5NjxbA0TaOlpYVLly7FBDbNwS2EsFTW+vr6pB4102aKt4Nu3rzJnj17cLvdIzyd8VBVlYGBAV544QWefPLJhMdMEq63trb+MtVBqSQN6HWe3yNDwc6JSJdEM5P5b25uLqFQiEgkwsDAAC6Xy1KXNE1j0aJFvPjiiyNiE4FAgJ///Ocj2o1XRZL1Ozs7m7y8PDo6OlBVNanb1SROTU0Ny5Yts+I4Fy5cGJHLtXjxYi5evMjt27dZuXIlra2tSClHzLqqqnLx4kVu3LgR099wOMzdu3ex2WwcPHhwhJ0RiUQIhUK88MILVFVVAbra+Pjjj/Poo48m7D/A+fPnOXr0KJ///OcpLS1N+rycTueI56WqKvPmzeNzn/sc2dnZSZ+xlJKBgQHeffdd0pjQM4pIJPIe+ngfFfbRfOtR+AXwmpRy4RRGYxMi2UyenZ1tpbT09fVRVFSE3W5naGjIym2y2+0jSBOJRGIGVfTMGn/NaAljIisri4qKCm7fvo3f7yc/P9/6PTr50bSramtrWb16NVJK2tvbuXbt2ogBlJ+fz9KlS7l06RJFRUVcuHCBJUuWkJeXF9N3m81GTU2N5TE0+3j37l06OzvZvHmzRdBo3L17l4MHD47w7GVnZ48w+KORk5ODzWazHAnx7yDZ2DDvzeVyUVZWRnzWcDz6+/tHZHFMNqSUre3t7b9I59i0GAOcAd4VQvzh+Ls1OTBfSH5+Pk8++SRCCFpbW7l79y579+4lJyeHcDic0FuWrL3RXr6ZXhItHZYuXcrly5fp7Oy0PHp+v5/m5mYqKyvJyspiaGiIcDhsDfzRiGmz2Vi1ahW3bt3i3XffJTs7e0SMBrASJKNJNzQ0xNWrV1m8eDFr1qwhPz9/BNl9Pp/lRUs044/1uUT3P9E5o50XL2WTtZVOHyYCIcS76OM8JezpDibgHSHEK8BIV8s0IhKJcO/ePTo6Orh37x7t7e10dnbicDgIhUIsXrzYch2ng1Q2SW9vr+XiBVi5ciWlpaXk5+fT2NjIkiVLsNlsVsrI/PnzycrKor+/H8CSRMlUTRMFBQVUVFRw5coVXnjhBYscoyEcDnP16lVu3brFtm3bRkgmM47S399PQUFBjFQZDylSId1YVzwSjcnJJAzQBrxjOi9SIV1JA7or7m3g3429TxPDaA8sEAhw/Phxurq6KCoqorKykqeffpri4mJLd25ra0NVVQKBwAg9ORgMjtiF2EwgDAQCDAwM4PV66enp4eOPP+batWuoqkpOTg6VlZWWavjwww9z6dIlVq1aRVlZmWWU22w2NE2jo6OD7OxscnJyYoKZ0deMvnZjYyO3bt2isrKSlpYW7ty5Q2VlZcIkRk3T6Ovr4/LlyzQ0NLBu3TrLVon3VKmqyr179ygtLbXSiNIZjPFOmPFgaGiIzs5OBgcHRz1uYGCAUCgU8120tJwE8ryNPr7Tgn2MHXgbeBlYOqYuTRCj9TE7O5tt27bhcDhwu92WERo9SABu3brF22+/HRMLMD09LS0tlmErhKCvr4+zZ89y+/ZtS0IUFxezcOFCFi5cSGlpKXl5eTidTsvwr6mp4dq1axw/fpzNmzfT0tJiuYHNgVpeXo7D4SASiRCJRAgEAiMIFAqFuHLlCsePH2fp0qU8/vjjXLlyhYMHD7Jp0yaWLVuG2+22VMD+/n5aWlosl/rmzZt5+OGHY9bsDA4OWh6xrq4umpubeeaZZ8a0ajL+HYxl8JqE83q9vP3229Z6oWQTgLmMIp1+ZAC30cd12hiLpAG9WNpPgT8e43mTAtPQLikpGWGsR8N0SS9btmyEdysUCuHz+WIWjJmkW7ZsGcXFxRQXF1NQUGDFSBJdY968eTzzzDMcPHiQt99+GyklGzduxOl0EggE8Pv9LF++HEVRuHPnDqdPn6avr4+srCwrvqIoCj6fj/b2dmpra1m3bh25ubls2LABu93O8ePHsdlsVFdXEw6HOXv2LNevXycvL4/q6moeeughioqKYly9gUCAY8eO0d6ub+IdDofxeDwsWrRozM/bZrONmpSZ6lyPx8OGDRtGNfKllNZiQvPvyVLLjLZ/ilEEMF0kKqyRCrXAT4A1Yz1xOmCqOqFQiOXLl8e8dDNfq7Gx0crZMokTvc4+3ZdmSpTr16/jdrtZtWoVOTk5aJpGZ2cn+fn5uN1uurq6uHTpEkIIlixZwqJFi1BVlatXrzJv3jzy8vLIz8+3UkpMe+r+/fvk5uZarvT29nbC4TDz58/H5XLFrMSM7tP9+/etjGq3222pZmMdjB0dHTQ1NVFbWxuTwZ0KZmJnZ2cn1dXVI1J24o8Nh8M0NDRQXFxsZRtMEs4DvwHUj+Wk8ZAG4GvA/xrPidMBMwo9Ve7yRAmR0Yi2D6JVlNEKYqQ74072zJxMrcr0uYm8apOAfw98Y6wnjbcUyE9IkZ8zk2AO3okasunANP5Hk1CmKpYqPpQo+yEVJnOQjXfiMe8tPrdstPcxBZPcHvRxPGaMlzQdwN8B6ftypwHxs1Wi4ORY20qFdKVBovOSxW4m0p9MIxOu59HszymCH338dozn5IkUnfoZ8MMJnD/pGG2mT4V002jGg5lIhunGFN/3D9HH77gw0UptPwTqJtjGjMR0zYIzQQ2bDkzh/dQxwcl+oqQ5D7wxwTbmMIepxBvo43bcEBnIJLXbbLbvAV9OeeQc5jC9+GFvb+9vAxMa9Jko9htpbW39HhNk7xzmMJmIRCLn33///e8xQcJA5vbMPhOJRL5DkpI3c5jDNCN88+bN75BmFnMqZLKs/PellN/KYHtzmEOm8C3g+5lqbLwZAcnwEPC3wI5MNjqHOUwAewcGBn4fuJmpBjO9gclN4JvAjQy3O4c5jAc30MdjxggDmScNwAfA3zBn38xhehGWUv4N+njMKCZrq6xvMY5EuDnMIYP4Bvo4zDgybdNEowz4K0YpazuHOWQKcdndbwH/AWifjGtN5qaM7eik+TD6y09rbtUcJhdRhPkQfdxNCmFgckkDcAH4n0QFPh+0nKk5zCicRx9vFybzIlOx/e8B4C+Apim41hw+vWhCH2cHJvtCU7Vn9tvA/2Cc6xfmMIcU6EAfX2MqkDFeTOVG89+XUv45MHr9njnMYWwYBP6cDEb8U2EqSQPw18B/A4am+LpzeDAxhD6e/noqLzrVpAH4i0gk8l+ZC37OYWII9/b2/ld0O2ZKMb4iVhPHnxv//imQfBfUOcwhDkY8ZsggzJ+nPGESMF2kAf2GVeBPgNHLyM9hDgaEEIPoKtmUSxgT00ka0G98CL1i5/xp7sscZj460Cfbv57OTkw3aUB/AIPAHwGV09uVOcxgNKG7lafMS5YMM4E0oD+IfuD/TndH5jAjcR5dK5mSOEwqTIf3LBlmxAOZw4zDh+hbV86Y8TFTJM0c5pAIb6EnX16Y5n7EYCZJGtB3tRbou639T+ZiOZ9WhNHf/39ghhEGZq6kaUcXyc3AHwLLp7c7c5hC3EBf+Ttji7TMVNKY+BbQCPwBc8U6Pg3Yi76mP+NLlDOJmaaeJcIHkUjk99Fd03Pq2oOJMPDXRtWYGU0YmB2kAb2ayNd6e3t/j7lKng8UIpHI+YaGht9D3ygso1VjJguzhTQmvt/b2/tVZvgWH3NIGz/cu3fvV5kBAcuxYKbbNIlwBn27hI+Bfw2snd7uzGEcqAPe6O3t/T4ZqK081ZiNpAH9QX8HfVfeLxuf7Gnt0RzSgR9dS/ghs1jNnq2kMXEe+H3gKPAvgZ3T2505jII96Fv2jXsHspmC2U4aEz8DDqFvb/06s2S79k8JzgNvom8K+0DUiHhQSAP6C/kGsA+9QOG/AJZOZ4c+5bgN/BQ9FaZ+eruSWTxIpDFRD/wnYDfwqvEph9gqjHEVGeeQObShJ1e+jW5zPnB4EElj4pTxeRf4IvAFIcRC88c5wmQcrejP+h3g2DT3ZVLxIJPGxDHgWG9v708LCwt/HXgZWDG9XXqgcD0SibzX3t7+i4ULF2Zkp7GZjk8DaUycQd/m8Md2u/3zwIvAU9Pcp9mMY8Du1tbWX5aVlTVMd2emEp8m0phoMD7/B91FvRM9GbR4Ojs1S9CFnlS5x/j0TmtvpgmfRtKY6EX37vwUeBzYbny2TmOfZioOA/uNz8fT3Jdpx6eZNNH42Pj8JfAssE1K+YwQYsNkX3g8XrxU52TIM3ga+Ag9/nWQuQxzC3OkiUUYPc6zTwjhArag2z2bgScBV6YvmGnCJGozTRIFgRNSyuNCiGPAEeO7OcRhjjTJEWRYJWFgYGBNbm7uBmA98ASwaqo7NF4JMso5V4CzkUjkTDAYPJ2bmztr88GmEnOkSR/njc93I5FIls1mWyOEWAM8anxWAfkTuUAqUoxXJTN+60cnySXjc76vr+98QUHBXDH6MWKONOPDEMPBUxMLgRr0GNBy4CFgGXoBRGd8A4kGebpSJAW5QuiF9W6hL+q6IYS4DlxDD0DOYYKYI03m0Gp84nfiKgcWARVA+cDAQJnL5Vpgt9tLgHlSykIhRD56Pets9ILwDkCRUmpCiDA6Sf3olUj7hRC9QPfAwECny+W6b7fb24UQbcBdoAU9lWUOk4T/Hynl+QSDAauRAAAAAElFTkSuQmCC");
            bais = new ByteArrayInputStream(bytes);
            bais2 = new ByteArrayInputStream(bytes2);
            er= ImageIO.read(bais);
            er2= ImageIO.read(bais2);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //创建图片
        BufferedImage img = new BufferedImage(430, 430, BufferedImage.TYPE_INT_RGB);

        try {
            //开启画图
            Graphics2D g = img.createGraphics();
            g.setColor(Color.WHITE);
            // 填充整个屏幕
            g.fillRect(0, 0, 430, 480);

            //写入二维码
            g.drawImage(er.getScaledInstance(430,430, Image.SCALE_DEFAULT), 0, 0, null);
            //写入白板
            g.drawImage(er2.getScaledInstance(200,200, Image.SCALE_DEFAULT), 115, 115, null);

//            //写入姓名
//            g.setFont(new Font("DejaVu Sans", Font.PLAIN, 22));
//            g.setColor(Color.black);
//            g.drawString("姓名：" + name, 20,480);
//

//            //写入官网
//            g.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
//            g.setColor(Color.gray);
//            g.drawString("www.yzyw8.com", 153,290);
//
            //写入电话
            g.setFont(new Font("DejaVu Sans", Font.PLAIN, 19));
            g.setColor(Color.gray);
            g.drawString(phone, 155,290);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(img,"png",stream);
            byte[] bytes1 = stream.toByteArray();
            String str = Base64.encodeBase64String(bytes1);

//            File file = new File("F:/新建文件夹/text.png");
//            if (file.exists() || ((file.getParentFile().exists() || file.getParentFile().mkdirs()) && file.createNewFile())) {
//                if (!ImageIO.write(img, "png", file)) {
//                    throw new IOException("Could not write an image of format " + "jpg" + " to " + file);
//                }
//            }

            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
/*

    public static String getImage(String base64,String name,String phone){
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes;
        byte[] bytes2;
        Image er=null;
        Image er2=null;
        ByteArrayInputStream bais = null;
        ByteArrayInputStream bais2 = null;
        try {
            bytes = decoder.decodeBuffer(base64);
            bytes2 = decoder.decodeBuffer("iVBORw0KGgoAAAANSUhEUgAAAFwAAAAYCAIAAAD4RA11AAAAhklEQVRYCe3WQQ6AIAxEUev974waEhPqX9caP7tOWMBjFsQYY3OtAvs6Ol0CokAPRBEFBCCyKaKAAEQ2RRQQgKhFU7p9IFugRMR8ryY6L6MkhVsHOl0YlaIkgvOaTRQSeDwPmnb8cCxtyld8RYGXEkUUEIDIpogCAhDZFFFAACKbIgoIQHQAvv0SJjH/FfwAAAAASUVORK5CYII=");
            bais = new ByteArrayInputStream(bytes);
            bais2 = new ByteArrayInputStream(bytes2);
            er= ImageIO.read(bais);
            er2= ImageIO.read(bais2);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //创建图片
        BufferedImage img = new BufferedImage(430, 430, BufferedImage.TYPE_INT_RGB);

        try {
            //开启画图
            Graphics2D g = img.createGraphics();
            g.setColor(Color.WHITE);
            // 填充整个屏幕
            g.fillRect(0, 0, 430, 480);

            //写入二维码
            g.drawImage(er.getScaledInstance(430,430, Image.SCALE_DEFAULT), 0, 0, null);
            //写入白板
            g.drawImage(er2.getScaledInstance(120,20, Image.SCALE_DEFAULT), 155, 270, null);

//            //写入姓名
//            g.setFont(new Font("DejaVu Sans", Font.PLAIN, 22));
//            g.setColor(Color.black);
//            g.drawString("姓名：" + name, 20,480);
//
//            //写入电话
//            g.setFont(new Font("DejaVu Sans", Font.PLAIN, 22));
//            g.setColor(Color.black);
//            g.drawString("电话：" + phone, 200,480);
//
//            //写入官网
//            g.setFont(new Font("DejaVu Sans", Font.PLAIN, 22));
//            g.setColor(Color.black);
//            g.drawString("官网：www.yzyw8.com", 20,520);

            //写入官网
            g.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
            g.setColor(Color.gray);
            g.drawString("www.yzyw8.com", 153,290);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(img,"png",stream);
            byte[] bytes1 = stream.toByteArray();
            String str = Base64.encodeBase64String(bytes1);

//            File file = new File("F:/新建文件夹/text.png");
//            if (file.exists() || ((file.getParentFile().exists() || file.getParentFile().mkdirs()) && file.createNewFile())) {
//                if (!ImageIO.write(img, "png", file)) {
//                    throw new IOException("Could not write an image of format " + "jpg" + " to " + file);
//                }
//            }

            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
*/

}
