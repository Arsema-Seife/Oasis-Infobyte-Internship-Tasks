const mongoose = require('mongoose');
const User = require('./User');

const adminSchema = new mongoose.Schema({
    email: { type: String, unique: true },
    password: String,
    name: String
});

module.exports = mongoose.model('Admin', adminSchema);
