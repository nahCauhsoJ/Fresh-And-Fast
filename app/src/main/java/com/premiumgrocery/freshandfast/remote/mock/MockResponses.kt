package com.premiumgrocery.freshandfast.remote.mock

object MockResponses {
    const val CATEGORIES_JSON = """
    {
      "count": 2,
      "data": [
        {
          "catDescription": "Fresh Fruits and Vegetables",
          "catId": 1,
          "catImage": "https://example.com/fruits.jpg",
          "catName": "Fruits & Vegetables",
          "_id": "cat1",
          "position": 1,
          "slug": "fruits-vegetables",
          "status": true,
          "__v": 0
        },
        {
          "catDescription": "Dairy and Bakery items",
          "catId": 2,
          "catImage": "https://example.com/dairy.jpg",
          "catName": "Dairy & Bakery",
          "_id": "cat2",
          "position": 2,
          "slug": "dairy-bakery",
          "status": true,
          "__v": 0
        }
      ],
      "error": false
    }
    """

    const val SUBCATEGORIES_JSON = """
    {
      "count": 2,
      "data": [
        {
          "catId": 1,
          "_id": "sub1",
          "position": 1,
          "status": true,
          "subDescription": "Fresh Fruits",
          "subId": 101,
          "subImage": "https://example.com/subfruits.jpg",
          "subName": "Fruits",
          "__v": 0
        },
        {
          "catId": 2,
          "_id": "sub2",
          "position": 1,
          "status": true,
          "subDescription": "Milk",
          "subId": 201,
          "subImage": "https://example.com/submilk.jpg",
          "subName": "Milk",
          "__v": 0
        }
      ],
      "error": false
    }
    """

    const val PRODUCTS_JSON = """
    {
      "count": 2,
      "data": [
        {
          "catId": 1,
          "created": "2023-01-01T00:00:00.000Z",
          "description": "Red Apple",
          "_id": "prod1",
          "image": "https://example.com/apple.jpg",
          "mrp": 1.5,
          "position": 1,
          "price": 1.2,
          "productName": "Apple",
          "quantity": 100,
          "status": true,
          "subId": 101,
          "unit": "kg",
          "__v": 0
        },
        {
          "catId": 2,
          "created": "2023-01-01T00:00:00.000Z",
          "description": "Fresh Milk",
          "_id": "prod2",
          "image": "https://example.com/milk.jpg",
          "mrp": 2.5,
          "position": 1,
          "price": 2.0,
          "productName": "Milk",
          "quantity": 50,
          "status": true,
          "subId": 201,
          "unit": "L",
          "__v": 0
        }
      ],
      "error": false
    }
    """

    const val PRODUCT_DETAILS_JSON = """
    {
      "data": {
          "catId": 1,
          "created": "2023-01-01T00:00:00.000Z",
          "description": "Red Apple",
          "_id": "prod1",
          "image": "https://example.com/apple.jpg",
          "mrp": 1.5,
          "position": 1,
          "price": 1.2,
          "productName": "Apple",
          "quantity": 100,
          "status": true,
          "subId": 101,
          "unit": "kg",
          "__v": 0
      },
      "error": false
    }
    """

    const val ORDER_REQUEST_RESPONSE_JSON = """
    {
      "error": false,
      "message": "Order placed successfully"
    }
    """

    const val ORDERS_JSON = """
    {
      "count": 1,
      "data": [
        {
          "date": "2023-01-01",
          "_id": "order1",
          "orderSummary": {
            "deliveryCharges": 0,
            "discount": 0,
            "_id": "summary1",
            "ourPrice": 10,
            "totalAmount": 10
          },
          "products": [],
          "shippingAddress": {
            "city": "Mock City",
            "houseNo": "123",
            "pincode": 123456,
            "streetName": "Mock Street",
            "type": "Home"
          },
          "user": {
            "email": "user1@example.com",
            "_id": "user1"
          },
          "userId": "user1",
          "__v": 0
        }
      ],
      "error": false
    }
    """
}
