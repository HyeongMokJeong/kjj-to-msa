package com.kjj.store.exception

import java.lang.Exception

class DataAlreadyExistsException(
    message: String
): Exception(message) {
}