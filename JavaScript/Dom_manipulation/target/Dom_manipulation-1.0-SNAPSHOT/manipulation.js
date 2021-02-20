let div1 = document.getElementById("div1");
let div2 = document.getElementById("div2");
let div3 = document.getElementById("div3");

document.getElementById('btn').onclick = function() {
    div1.style.height = "200px";
    div2.style.height = "200px";
    div3.style.height = "200px";
    div1.style.backgroundColor = 'green';
    div2.style.backgroundColor = 'red';
    div3.style.backgroundColor = 'blue';
};