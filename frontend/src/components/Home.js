import React, { useState, useEffect } from 'react';
import { productService } from '../services/api';

const Home = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    productService.getAll()
      .then(res => setProducts(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div className="container mt-4">
      <div className="p-5 mb-4 bg-light rounded-3 text-center">
        <div className="container-fluid py-5">
          <h1 className="display-5 fw-bold">SK Bazaar</h1>
          <p className="col-md-8 fs-4 mx-auto italic">Har Zaroorat, Ek Bazaar</p>
        </div>
      </div>

      <h2 className="mb-4">Featured Products</h2>
      <div className="row row-cols-1 row-cols-md-3 g-4">
        {products.map(product => (
          <div className="col" key={product.id}>
            <div className="card h-100">
              <div className="card-body">
                <h5 className="card-title">{product.name}</h5>
                <p className="card-text text-muted">{product.brand}</p>
                <h6 className="card-subtitle mb-2 text-primary">₹{product.price}</h6>
                <button className="btn btn-primary w-100">Add to Cart</button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Home;
