import { format } from 'date-fns';

export const toBrFormatDate = (date : string) => date ? format(new Date(date), 'dd/MM/yyyy HH:mm') : '';

export const isPastDate = (date : string) => new Date(date) < new Date();

export const toGlobalDate = (date : string) => new Date(date).toISOString();

export const toLocalDate = (date : string) => date ? format(new Date(date), 'yyyy-MM-dd') + 'T' + format(new Date(date), 'HH:mm') : '';
