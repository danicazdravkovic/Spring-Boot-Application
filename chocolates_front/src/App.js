import './App.css';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Navbar from './layout/Navbar';
import Home from './pages/Home';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AddCategory from './chocolate_categories/AddCategory';
import EditCategory from './chocolate_categories/EditCategory';
import ViewCategory from './chocolate_categories/ViewCategory';
import AddChocolate from './chocolates/AddChocolate';
import EditChocolate from './chocolates/EditChocolate';
import ViewChocolate from './chocolates/ViewChocolate';
import AddCustomer from './customers/AddCustomer';
import Cart from './customers/Cart';
import EditCustomer from './customers/EditCustomer';
import Login from './pages/Login';
import ViewCategories from './chocolate_categories/ViewCategories';
import Orders from './admins/Orders';
import AdminChocolatesView from './chocolates/AdminChocolatesView';
import EditOrder from './orders/EditOrder';
import Statistics from './pages/Statistics';

function App() {
  return (
    <div className="App">
      <Router>
        <Navbar />

        <Routes>
          <Route exact path="/" element={<Login />} />
          <Route exact path="/index" element={<Home />} />
          {/* <Route exact path="/index/customer" element={<HomeCustomer />} /> */}
          <Route exact path="/cart" element={<Cart />} />
          <Route exact path="/orders" element={<Orders />} />

          <Route exact path="/editorder/:id" element={<EditOrder />} />

          <Route exact path="/addcategory" element={<AddCategory />} />
          <Route exact path="/editcategory/:id" element={<EditCategory />} />
          <Route exact path="/viewcategory/:id" element={<ViewCategory />} />
          <Route exact path="/viewcategories" element={<ViewCategories />} />

          <Route exact path="/addchocolate" element={<AddChocolate />} />
          <Route exact path="/editchocolate/:id" element={<EditChocolate />} />
          <Route exact path="/viewchocolate/:id" element={<ViewChocolate />} />
          <Route exact path="/viewchocolates" element={<AdminChocolatesView />} />

          <Route exact path="/addcustomer" element={<AddCustomer />} />
          <Route exact path="/editcustomer" element={<EditCustomer />} />
          <Route exact path="/statistics" element={<Statistics />} />



          {/* <Route exact path="/customer/login" element={<CustomerLogin />} /> */}



        </Routes>
      </Router>
    </div>
  );
}

export default App;
