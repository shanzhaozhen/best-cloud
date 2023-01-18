export const isProduction = process.env.NODE_ENV === 'production';
export const apiRoot = isProduction ? '' : '/api';

export const fileUploadURL = isProduction ? '' : '/api/uaa/files';
