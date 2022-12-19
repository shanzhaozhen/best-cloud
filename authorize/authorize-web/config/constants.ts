export const isProduction = process.env.NODE_ENV === 'production';
export const resourcesPath = isProduction ? '/front/' : '/';
export const apiRoot = isProduction ? '' : '/api';
