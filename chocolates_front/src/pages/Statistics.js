import React, { useEffect, useState } from 'react'
import axios from 'axios';
import Plot from 'react-plotly.js';


// export default function Statistics(event) {

//     const loggedUser = getLoggedUser()

//     const [chocolates, setChocolates] = useState([])
//     const [customers, setCustomers] = useState([])
//     const [categories, setCategories] = useState([])
//     const [orders, setOrders] = useState([])
//     const [favouriteChocolate, setFavouriteChocolate] = ({})


//     useEffect(() => {

//         loadChocolates();
//         loadCustomers();
//         loadCategories();
//         loadOrders();
//     }, []);
//     const loadChocolates = async () => {
//         const result = await axios.get("http://localhost:8080/chocolates");
//         // console.log(result.data);
//         setChocolates(result.data);
//     }
//     const loadCustomers = async () => {
//         const result = await axios.get("http://localhost:8080/customers");
//         setCustomers(result.data);
//     }
//     const loadCategories = async () => {
//         const result = await axios.get("http://localhost:8080/categories");
//         setCategories(result.data);
//     }
//     const loadOrders = async () => {
//         const result = await axios.get("http://localhost:8080/orders");
//         setOrders(result.data);
//     }
//     var myChart = new Chart("myChart", {
//         type: "scatter",
//         data: {},
//         options: {}
//       });
//     return(
//         <div>
// <script
// src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js">
// </script>

// <canvas id="myChart" style="width:100%;max-width:700px"></canvas>


//         </div>

//     )
// }

export default function Statistics() {


    const [orderItems, setOrderItems] = useState([])


    useEffect(() => {
        loadOrderItems();
    }, []);

    const loadOrderItems = async () => {
        const result = await axios.get("http://localhost:8080/orderItems");
        setOrderItems(result.data);
    }
 
    let x_duplicates = orderItems.map(orderItem => orderItem.chocolateDto.name);
    let x = [];

    x_duplicates.forEach((el) => {
        if (!x.includes(el)) {
            x.push(el);
        }
    });

    let number=0;
    let y=[]
    x.forEach(el1 => {
        number=0
        orderItems.forEach(el2 => {
            if(el1===el2.chocolateDto.name){
                number+=el2.quantity
            }
        });
        y.push(number)
    });

    return (
        <Plot
            data={[
                { type: 'bar', x: x, y: y },
            ]}
            layout={{ width: 1000, height: 500, title: 'Statistics',
              xaxis: {
                title: {
                  text: 'Chocolate',
                  font: {
                    family: 'Courier New, monospace',
                    size: 18,
                    color: '#7f7f7f'
                  }
                }
              },

              yaxis: {
                title: {
                  text: 'Number of orders   ',
                  font: {
                    family: 'Courier New, monospace',
                    size: 18,
                    color: '#7f7f7f'
                  }
                }
              }
             }}
        />
    );
}
