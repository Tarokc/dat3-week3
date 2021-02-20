const names = ["Lars", "Jan", "Peter", "Bo", "Frederik"];

document.getElementById("btn").onclick = function(e) {
    names.push(document.getElementById("name").value);
    showNames();
    e.preventDefault();
};

function toHtmlListStr(names) {
    var listStart = "<ul><li>";
    var listEnd = "</li></ul>";
    var htmlListStr = listStart.concat(names.map(name => name).join("</li><li>").concat(listEnd));
    return htmlListStr;
};

function listItems(names) {
    return (names.map(name => `<li>${name}</li>`)).join("");
};

function showNames() {
    document.getElementById("list").innerHTML = listItems(names);
};

document.getElementById("removeFirst").onclick = function(e) {
    e.preventDefault();
    names.shift();
    showNames();
};

document.getElementById("removeLast").onclick = function(e) {
    e.preventDefault();
    names.pop();
    showNames();
};

showNames();

