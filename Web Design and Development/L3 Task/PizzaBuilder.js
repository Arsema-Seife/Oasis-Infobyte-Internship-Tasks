import React, { useState } from 'react';

const PizzaBuilder = () => {
    const [base, setBase] = useState('');
    const [sauce, setSauce] = useState('');
    const [cheese, setCheese] = useState('');
    const [veggies, setVeggies] = useState([]);
    const [meat, setMeat] = useState([]);

    const handleAddVeggie = (v) => setVeggies([...veggies, v]);

    const handlePlaceOrder = () => {
        const order = { base, sauce, cheese, veggies, meat };
        console.log('Order:', order);
        // Call backend API to create order and integrate Razorpay
    }

    return (
        <div>
            <h2>Build Your Pizza</h2>
            <div>
                <h3>Choose Base</h3>
                <select onChange={e=>setBase(e.target.value)}>
                    <option value="">Select Base</option>
                    <option value="Thin">Thin</option>
                    <option value="Thick">Thick</option>
                </select>
            </div>
            <div>
                <h3>Choose Sauce</h3>
                <select onChange={e=>setSauce(e.target.value)}>
                    <option value="">Select Sauce</option>
                    <option value="Tomato">Tomato</option>
                    <option value="Barbecue">Barbecue</option>
                </select>
            </div>
            <button onClick={handlePlaceOrder}>Place Order</button>
        </div>
    );
}

export default PizzaBuilder;
