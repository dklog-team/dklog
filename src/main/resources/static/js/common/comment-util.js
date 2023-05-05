const getNewCommentListHtml = (newCommentList, memberId) => {
    let newCommentListHtml = ``;
    newCommentList.forEach((comment) => {
        newCommentListHtml += `
            <div class="my-8">
                <div class="flex justify-between items-center mb-6">
                    <div class="flex items-center gap-x-4">
                        <div class="avatar">
                            <div class="w-10 rounded-full ring-2 ring-gray-300">
                                <img src="${comment.picture}"/>
                            </div>
                        </div>
                        <div>
                            <div>
                                <span>${comment.username}</span>
                            </div>
                            <div>
                                <span class="text-sm text-gray-400">${comment.writeDate}</span>
                            </div>
                        </div>
                    </div>
                `;
        if (memberId == comment.memberId) {
            newCommentListHtml += `
                    <div class="dropdown">
                        <label tabindex="0" class="btn btn-ghost btn-sm px-1">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M6.75 12a.75.75 0 11-1.5 0 .75.75 0 011.5 0zM12.75 12a.75.75 0 11-1.5 0 .75.75 0 011.5 0zM18.75 12a.75.75 0 11-1.5 0 .75.75 0 011.5 0z" />
                            </svg>
                        </label>
                        <ul tabindex="0" class="dropdown-content menu p-2 shadow bg-base-100 rounded-box w-auto">
                            <li>
                                <a id="updateCommentBtn${comment.commentId}" class="text-gray-700 block px-4 py-2 text-sm flex" role="menuitem" tabindex="-1" id="postEditBtn">
                                    <div class="w-1/5">
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                            <path stroke-linecap="round" stroke-linejoin="round" d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                                        </svg>
                                    </div>
                                    <div class="w-4/5 ml-2 text-center">
                                        수정
                                    </div>
                                </a>
                            </li>
                            <li>
                                <a id="deleteCommentBtn${comment.commentId}" onclick="clickDeleteCommentBtn(${comment.commentId})" class="text-gray-700 block px-4 py-2 text-sm flex" role="menuitem" tabindex="-1">
                                    <div class="w-1/5">
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                                            <path stroke-linecap="round" stroke-linejoin="round" d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
                                        </svg>
                                    </div>
                                    <div class="w-4/5 ml-2 text-center">
                                        삭제
                                    </div>
                                </a>
                            </li>
                        </ul>
                    </div>
            `;
        }
        newCommentListHtml += `
                </div>
                <div class="mb-8">
                    <p class="whitespace-pre-wrap break-words">${comment.content}</p>
                </div>
                <hr/>
            </div>
        `;
    });
    return newCommentListHtml;
}