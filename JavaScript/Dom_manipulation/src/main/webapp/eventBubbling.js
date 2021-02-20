document.getElementById("div1").onclick = function() {
    console.log("Hello from " + this.id);
};

document.getElementById("div2").onclick = function() {
    console.log("Hello from " + this.id);
};

          
document.getElementById("outer").onclick = function(e) {
    document.getElementById("para").innerText = "Hello from " + e.target.id;
};

