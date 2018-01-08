package app.server.authorization

import app.shared.data.ref.uuid.UUID

sealed trait Identity {
}

case class UserIdentity(userID:UUID) extends Identity

