package com.kjj.store.exception

import java.lang.Exception

class CantFindByIdException(
    message: String
): Exception(message) {
}