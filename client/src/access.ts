/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
import {CurrentUser} from "@/services/uaa/type/user";

export default function access(initialState: { currentUser?: CurrentUser } | undefined) {
  const { currentUser } = initialState ?? {};
  return {
    canAdmin: currentUser && currentUser.access === 'admin',
  };
}
