const mongoose = require('mongoose');

const orderSchema = new mongoose.Schema({
    user: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
    pizza: { type: mongoose.Schema.Types.ObjectId, ref: 'Pizza' },
    customizations: {
        base: String,
        sauce: String,
        cheese: String,
        veggies: [String],
        meat: [String]
    },
    status: { type: String, default: 'Order Received' }, // "In Kitchen", "Out for Delivery"
    totalPrice: Number,
    createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('Order', orderSchema);
