# CoffeeConnect Best Practices

## API Integration
- Always use **HTTPS** for all endpoints.
- Include `Authorization: Bearer <token>` header for authenticated requests.
- Handle rate limiting: retry with exponential backoff on `429 Too Many Requests`.
- Validate responses with status codes: `200` success, `201` created, `400` bad request, `401` unauthorized.

## Order Flow
1. **Create Order**: `POST /api/orders` with `{ "items": [...], "storeId": "..." }`.
2. **Confirm Payment**: Wait for webhook `payment.confirmed` before updating order status.
3. **Update Status**: Use `PATCH /api/orders/{id}` with status transitions: `pending → confirmed → brewing → ready → completed`.

## Error Handling
- Parse error response body: `{ "error": { "code": "...", "message": "..." } }`.
- Log all errors with request ID for debugging.
- Display user-friendly messages in UI (e.g., "Could not place order, please try again").

## Caching
- Cache menu items for 5 minutes using `localStorage` or `Redis`.
- Invalidate cache on `menu.updated` webhook.

## Testing
- Use `POSTMAN` or `curl` for manual API tests.
- Write unit tests for order validation logic.
- Mock external services (payment, inventory) in integration tests.