package app.server.authorization

import app.shared.model.ref.uuid.UUID

sealed trait Identity {
}

case class UserIdentity(userID:UUID) extends Identity

