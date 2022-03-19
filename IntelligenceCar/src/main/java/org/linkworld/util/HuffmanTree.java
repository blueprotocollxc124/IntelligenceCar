package org.linkworld.util;

import java.util.*;

public class HuffmanTree {

    private static char[] cha;

    private static char c1='0';

    private static char c2='1';

    private static List<Character> code=new ArrayList<>(100000000);

    private static int headLength=40;

    private static int charLength=16;

    private static int codeValueLength=16;


    private int weight;

    private Character character;

    private HuffmanTree lTree;

    private HuffmanTree rTress;

    private int getWeight() {
        return weight;
    }

    private void setWeight(int weight) {
        this.weight = weight;
    }

    private Character getCharacter() {
        return this.character;
    }

    private void setCharacter(Character character) {
        this.character = character;
    }

    private HuffmanTree getlTree() {
        return this.lTree;
    }

    private void setlTree(HuffmanTree lTree) {
        this.lTree = lTree;
    }

    private HuffmanTree getrTress() {
        return this.rTress;
    }

    private void setrTress(HuffmanTree rTress) {
        this.rTress = rTress;
    }

    private HuffmanTree createTree(char[] con){
        HuffmanTree[] huffmanTrees=  count(con);
        int startIndex=0;
        while (startIndex<=huffmanTrees.length-2){
            HuffmanTree huffmanTree=new HuffmanTree(huffmanTrees[startIndex]
                    ,huffmanTrees[startIndex+1],null);
            startIndex++;
            shellSort(huffmanTrees,huffmanTree,startIndex);
        }
        this.setlTree(huffmanTrees[huffmanTrees.length-2]);
        this.setrTress(huffmanTrees[huffmanTrees.length-1]);
        this.setWeight(rTress.getWeight()+ lTree.getWeight());
        return this;
    }

    private void ListChangeArray(List<Character> code,char[] cha){
        for(int i=0;i<code.size();i++){
            cha[i]=code.get(i);
        }

        code.remove(code.size()-1);
    }

    private void getCode(Map<Character,String> codes,HuffmanTree tree,List<Character> list,List<Character> code,char c1,char c2){
        HuffmanTree lTress=tree.getlTree();
        HuffmanTree rTress=tree.getrTress();
        if(lTress!=null&&lTress.getCharacter()!=null){
            code.add(c1);
            this.cha=new char[code.size()];
            ListChangeArray(code,cha);
            codes.put(lTress.getCharacter(),String.valueOf(cha));
            list.add(lTress.getCharacter());


        }
        if(rTress!=null&&rTress.getCharacter()!=null){
            code.add(c2);
            this.cha=new char[code.size()];
            ListChangeArray(code,cha);
            codes.put(rTress.getCharacter(),String.valueOf(cha));
            list.add(rTress.getCharacter());
        }
        if(lTress!=null) {
            code.add(c1);
            getCode(codes,lTress,list,code,c1,c2);
            code.remove(code.size()-1);
        }

        if (rTress!=null) {
            code.add(c2);
            getCode(codes,rTress,list,code,c1,c2);
            code.remove(code.size()-1);
        }

    }
    private HuffmanTree(HuffmanTree lTree,HuffmanTree rTree,Character chara){
        this.weight= lTree.getWeight()+rTree.getWeight();
        this.setlTree(lTree);
        this.setrTress(rTree);
        this.character=chara;
    }

    private HuffmanTree(int weight,Character chara){
        this.weight= weight;
        this.character=chara;
    }

    public HuffmanTree(){

    }

    public byte[] encrypt(String con){
        char[] con1=new char[con.length()];
        con.getChars(0,con.length(),con1,0);
        createTree(con1);
        Map<Character,String> codes=new HashMap<>();
        List<Character> key=new ArrayList<>();
        getCode(codes,this,key,this.code,c1,c2);
        StringBuffer charCode=new
                StringBuffer();
        for(int i=0;i< con1.length;i++){
            charCode.append(codes.get(con1[i]));
        }
        int length=charCode.length();
        if(charCode.length()%8!=0){
            for(int i=0;i<charCode.length()%8;i++){
                charCode.append("0");
            }
        }
        byte[] code = new byte[(charCode.length()/8)+5];

        String charCodes=charCode.toString();

        stringToByteArray(charCodes,code,5);
        stringToByteArray(charCodes,code,5);

        addByte(code,0,5,intToByteArray(length));

        byte[] bytes=charChangeByte(codes,key);

        byte[] allCode=new byte[bytes.length+code.length];
        for(int i=0;i<allCode.length;i++){
            if(i<code.length){
                allCode[i]=code[i];
            }else{
                allCode[i]=bytes[i-code.length];
            }
        }
        return allCode;
    }

    private byte[] charChangeByte(Map<Character,String> chars, List<Character> list){

        String cache="";
        for(int i=0;i<list.size();i++){
            String num=Integer.toBinaryString(chars.get(list.get(i)).length());
            String cha= Integer.toBinaryString(list.get(i));
            while(num.length()%16!=0){
                num="0"+num;
            }
            while(cha.length()<16){
                cha="0"+cha;
            }
            cache=cache+cha+num+chars.get(list.get(i));
        }
        while (cache.length()%8!=0){
            cache=cache+"0";
        }
        byte[] bytes=stringToByteArray(cache);
        return bytes;
    }

    private HuffmanTree[] count(char[] con){
        int length=con.length;
        if(length<=0){
            throw new RuntimeException();
        }
        Map<Character,Integer> cache=new HashMap<>();
        List<Character> list=new ArrayList<>();
        for(int i=0;i<length;i++){
            if(cache.get(con[i])==null){
                cache.put(con[i],1);
                list.add(con[i]);
            }else {
                cache.replace(con[i],cache.get(con[i])+1);
            }
        }
        HuffmanTree[] huffmanTrees =createTreeByMap(cache,list);
        mergeSort(huffmanTrees,0,huffmanTrees.length-1);
        return huffmanTrees;
    }

    private HuffmanTree[] createTreeByMap(Map<Character,Integer> cache,List<Character> list){
        HuffmanTree[] tress=new HuffmanTree[cache.size()];
        for(int i=0;i<list.size();i++){
            tress[i]=new HuffmanTree(cache.get(list.get(i)),list.get(i));
        }
        return tress;
    }

    private void mergeSort(HuffmanTree[] con,int start,int end){
        int co=(start+end)/2;
        int start2=co+1;
        int end1=co;
        if(start>=end){
            return;
        }
        mergeSort(con,start,end1);
        mergeSort(con,start2,end);
        int l=start;
        int r=start2;
        while(l<=r&&r<=end){
            if (con[l].getWeight() > con[r].getWeight()) {
                HuffmanTree huffmanTree = con[r];
                for (int i = r; i > l; i--) {
                    con[i] = con[i - 1];
                }
                con[l] = huffmanTree;
                r++;
            }
            l++;
        }

    }

    private void shellSort(HuffmanTree[] tress,HuffmanTree tree,int startIndex){
        for(int i=startIndex+1;i<tress.length;i++){
            if(tress[i].getWeight()<tree.getWeight()){
                tress[i-1]=tress[i];
                if(i== tress.length-1){
                    tress[i]=tree;
                    return;
                }
            }else {
                tress[i-1]=tree;
                return;
            }


        }
    }



    private byte[] stringToByteArray(String bin){
        byte[] by =new byte[bin.length()/8];
        int startIndex=0;
        int start=0;
        while (startIndex<bin.length()/8){
            by[startIndex++] = (byte) Integer.
                    parseInt(bin.substring(start,  start+8), 2);
            start+=8;
        }
        return by;
    }

    private void addByte(byte[] be,int startIndex,int endIndex,byte[] add){
        for(int i= startIndex;i<endIndex;i++){
            be[i]=add[i-startIndex];
        }
    }

    private byte[] intToByteArray(int i){
        String bin=Integer.toBinaryString(i);
        if(bin.length()>40){
            throw new RuntimeException();
        }
        while (bin.length()%40!=0){
            bin="0"+bin;
        }
        return stringToByteArray(bin);
    }

    private void stringToByteArray(String bin,byte[] be,int startIndex){
        int start=0;
        while (startIndex<be.length){
            be[startIndex++] = (byte) Integer.
                    parseInt(bin.substring(start,  start+8), 2);
            start+=8;
        }
    }


    public char[] decode(byte[] con){
        int length=getLength(con,0,headLength/8);

        int codeIndex;
        if(length%8!=0){
            codeIndex=(headLength+length)/8+1;
        }else {
            codeIndex=(headLength+length)/8;
        }
        Map<String,Character> code=getCode(con,codeIndex,con.length);

        return getText(con,code,length);
    }

    private int getLength(byte[] con,int startIndex,int endIndex){
        StringBuffer s =new StringBuffer();
        for(int i=startIndex;i<endIndex;i++){
            s.append(byteToString(con[i]));
        }
        while (s.charAt(0)=='0'){
            s.delete(0,1);
        }
        return Integer.parseInt(s.toString(),2);
    }

    private Map<String,Character> getCode(byte[] con,int startIndex,int endIndex){
        Map<String,Character> cache=new HashMap<>();
        StringBuffer s=new StringBuffer();
        for(int i=startIndex;i<endIndex;i++){
            s.append(byteToString(con[i]));
        }
        while (s.length()>=8) {
            String cha1=s.substring(0,charLength).toString();
            s.delete(0,codeValueLength);
            Character ca=(char)Integer.parseInt(cha1,2);
            String le=s.substring(0,codeValueLength).toString();
            s.delete(0,codeValueLength);
            int len=Integer.parseInt(le,2);
            String code=s.substring(0,len).toString();
            s.delete(0,len);
            cache.put(code,ca);
        }
        return cache;
    }

    private char[] getText(byte[] con,Map<String,Character> code,int length){
        List<Character> list=new LinkedList<Character>();
        StringBuffer s=new StringBuffer();
        StringBuffer s1=new StringBuffer("");
        int conf ;
        if(length%8!=0){
            conf=(headLength+length)/8+1;
        }else {
            conf=(headLength+length)/8;
        }
        for(int i=headLength/8;i<conf;i++){
            s.append(byteToString(con[i]));
        }

        for(int i=0;i<length;i++){
            s1.append(s.substring(i,i+1));
            Character character=code.get(s1.toString());
            if(character!=null){
                list.add(character);
                s1=new StringBuffer("");
            }
        }
        char[] text=new char[list.size()];
        for(int i=0;i<list.size();i++){
            text[i]=list.get(i);
        }
        return text;
    }

    private String byteToString(byte b){
        return Integer.toBinaryString((b&0xff)+0x100).substring(1);
    }

    @Override
    public String toString() {
        return "HuffmanTree{" +"\n"+
                "weight=" + weight +"\n"+
                ", character=" + character +"\n"+
                ", lTree=" + lTree +"\n"+
                ", rTress=" + rTress +"\n"+
                '}';
    }
}
