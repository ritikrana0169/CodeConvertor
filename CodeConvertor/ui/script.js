require.config({ paths: { 'vs': 'https://cdn.jsdelivr.net/npm/monaco-editor@0.27.0/min/vs' } });

require(['vs/editor/editor.main'], function() {
    monaco.editor.setTheme('vs-dark');

    var editor = monaco.editor.create(document.getElementById('container'), {
        value: "",
        language: "javascript"
    });
    var requestValue="";
    var resultPannel=document.getElementById("resultPannel");
    var language="javascript";
    var languageVal=document.getElementById("languageSelect");
    var convert = document.getElementById('convertCode');
    var debugCode=document.getElementById("debugCode");
   var checkQuality=document.getElementById("checkQuality");
    languageVal.addEventListener("change",()=>{
        language=languageVal.value;
    })
    convert.addEventListener('click', function() {
        let code = editor.getValue();
       
        requestValue="Convert this code "+code+" in this "+ language;
        console.log(requestValue)
        getData()
        // resultPannel.innerText=requestValue;
    });
    debugCode.addEventListener("click",()=>{
        let code=editor.getValue();
        requestValue="Debug the given  code "+code;
        getData()
        // resultPannel.innerText=requestValue;
    })
    checkQuality.addEventListener("click",()=>{
        let code=editor.getValue();
        requestValue="Check the Quality of given  code "+code;
        getData()
        // resultPannel.innerText=requestValue;
    })
    const apiUrl = `http://localhost:8080/chat?prompt=`;

    function getData() {
        
       

        fetch(apiUrl+requestValue, {
            method: 'GET'
        })
        .then(response => response.text())
        .then(data => {
          
            const resultPannel = document.getElementById('resultPannel');
            console.log(JSON.stringify(data))
            resultPannel.innerHTML =data;
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
    }



});
