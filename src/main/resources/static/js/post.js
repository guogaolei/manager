document.write("<script language=javascript src='js/ras.js'></script>");
document.write("<script language=javascript src='js/jquery.min.js'></script>");
function post(datas,URL,successfn){
    datas=Base64.encode(datas);
    var encryptor = new JSEncrypt();
    var pubKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXl1duKjOGcYND2prtZKkp9ILKOBuB0+JZ5+ug5kBM8IL4ty7RBYiVjtT1XdAIoOKCLiXB6SscAyg1N6vPpf83dqIG8ZedvZYldxAEvVB+m0any4KiHBMngj1FuvlzljQpOw8FD0an2EbUypEkChejWowWs9P+cLNTClY8K2QKcwIDAQAB";
    encryptor.setPublicKey(pubKey)//设置公钥
    datas = encryptor.encrypt(datas);
    $.ajax({
        url : URL,
        type : 'post',
        dataType : 'json',
        contentType :"application/json",
        data : datas,
        async:false,
        success : function (result) {
            successfn(result);
        }
    })
}