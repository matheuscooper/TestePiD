# Industrial Production Manager — API Configuration

## Connecting to Your Backend

This frontend is designed to connect to a REST API backend. To configure the backend URL:

1. In the **Settings > Secrets** panel of the Management UI, add the secret:
   - Key: `VITE_API_BASE_URL`
   - Value: Your backend URL (e.g., `https://api.yourbackend.com`)

2. If no `VITE_API_BASE_URL` is set, the app will use an empty base URL (relative paths), which works when the frontend and backend are served from the same domain.

## API Endpoints Used

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/products | List all products |
| GET | /api/products/:id | Get product details |
| POST | /api/products | Create product |
| PUT | /api/products/:id | Update product |
| DELETE | /api/products/:id | Delete product |
| GET | /api/raw-materials | List all raw materials |
| GET | /api/raw-materials/:id | Get raw material details |
| POST | /api/raw-materials | Create raw material |
| PUT | /api/raw-materials/:id | Update raw material |
| DELETE | /api/raw-materials/:id | Delete raw material |
| POST | /api/products/:id/raw-materials | Associate raw material to product |
| GET | /api/production/suggestion | Get production suggestion |
