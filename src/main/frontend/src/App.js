import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login/login';
import Signup from './components/Signup/signup';
import Home from './components/Home/home';
import Account from './components/Account/account';
import CreateAccount from './components/Account/create/createAccount';
import AccountRecord from './components/Account/detail/accountRecord';
import Contact from './components/Contact/contact';
import CreateContact from './components/Contact/create/createContact';
import ContactRecord from './components/Contact/detail/contactRecord';
import Opportunity from './components/Opportunity/opportunity';
import CreateOpportunity from './components/Opportunity/create/createOpportunity';
import OpportunityRecord from './components/Opportunity/detail/opportunityRecord';
import Order from './components/Order/order';
import CreateOrder from './components/Order/create/createOrder';
import OrderRecord from './components/Order/detail/orderRecord';

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/login" element={<Login />} />
                    <Route path="/signup" element={<Signup />} />
                    <Route path="/home" element={localStorage.getItem("token") !== null ? <Home /> : <Navigate to="/login" />} />
                    <Route path="/account" element={<Account />} />
                    <Route path="/account/create" element={<CreateAccount />} />
                    <Route path="/account/update" element={<CreateAccount />} />
                    <Route path="/account/details/:id" element={<AccountRecord />} />
                    <Route path="/contact" element={<Contact />} />
                    <Route path="/contact/create" element={<CreateContact />} />
                    <Route path="/contact/update" element={<CreateContact />} />
                    <Route path="/contact/details/:id" element={<ContactRecord />} />
                    <Route path="/opportunity" element={<Opportunity />} />
                    <Route path="/opportunity/create" element={<CreateOpportunity />} />
                    <Route path="/opportunity/update" element={<CreateOpportunity />} />
                    <Route path="/opportunity/details/:id" element={<OpportunityRecord />} />
                    <Route path="/order" element={<Order />} />
                    <Route path="/order/create" element={<CreateOrder />} />
                    <Route path="/order/update" element={<CreateOrder />} />
                    <Route path="/order/details/:id" element={<OrderRecord />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
