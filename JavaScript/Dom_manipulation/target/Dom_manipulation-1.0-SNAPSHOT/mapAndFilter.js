var cars = [
  { id: 1, year: 1997, make: 'Ford', model: 'E350', price: 3000 },
  { id: 2, year: 1999, make: 'Chevy', model: 'Venture', price: 4900 },
  { id: 3, year: 2000, make: 'Chevy', model: 'Venture', price: 5000 },
  { id: 4, year: 1996, make: 'Jeep', model: 'Grand Cherokee', price: 4799 },
  { id: 5, year: 2005, make: 'Volvo', model: 'V70', price: 44799 }
];

function getTableHeaders() {
    return `<thead><tr>` + Object.keys(cars[0]).map(key => `<th>` + key + `</th>`).join("") + `</tr></thead>`;
};

function getTableRows(cars) {
    return cars.map(car => `<tr>` + Object.values(car).map(value => `<td>` + value + `</td>`).join("") + `</tr>`).join("");
};

function createTable(cars) {
    tableData = getTableHeaders().concat(getTableRows(cars));
    document.getElementById("table").innerHTML = tableData;
};

createTable(cars);

document.getElementById("btn").onclick = function (e) {
    e.preventDefault();
    price = document.getElementById("price").value;
    let filteredCars = cars.filter(car => car.price < price);
    createTable(filteredCars);
};