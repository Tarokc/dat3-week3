/* global fetch */


function fetchAll(url) {
   fetch(url)
  .then(res => res.json()) //in flow1, just do it
  .then(data => {
   // Inside this callback, and only here, the response data is available
   document.getElementById("tableBody").innerHTML = ("data", data.map(data => `<tr class="row">` + `<td class="col ml-4">` + data.name + `</td><td class="col">` + data.phone + `</td></tr>`).join(""));
  /* data now contains the response, converted to JavaScript
     Observe the output from the log-output above
     Now, just build your DOM changes using the data*/       
    });
};

function fetchUser(url) {
   fetch(url)
  .then(res => res.json()) //in flow1, just do it
  .then(data => {
   // Inside this callback, and only here, the response data is available
     console.log(data); 
   document.getElementById("userInfo").innerHTML = data;
  /* data now contains the response, converted to JavaScript
     Observe the output from the log-output above
     Now, just build your DOM changes using the data*/       
    });
};

document.getElementById("allBtn").onclick = function(e) {
    let url = "https://jsonplaceholder.typicode.com/users";
    e.preventDefault();
    document.getElementById("userInfo").innerHTML = "";
    document.getElementById("tableHead").innerHTML = `<tr class="row"><th class="col ml-4">Name</th><th class="col">Phone</th></tr>`;
    fetchAll(url);
};

document.getElementById("userBtn").onclick = function(e) {
let url = "https://jsonplaceholder.typicode.com/users/1";
    e.preventDefault();
    fetchUser(url);
};


